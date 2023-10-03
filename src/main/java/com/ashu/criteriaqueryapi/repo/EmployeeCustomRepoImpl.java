package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.dto.EmployeeNameAndCityDTO;
import com.ashu.criteriaqueryapi.dto.EmployeeNamesPincodeDTO;
import com.ashu.criteriaqueryapi.model.Address;
import com.ashu.criteriaqueryapi.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeCustomRepoImpl implements EmployeeCustomRepo {

    @Autowired
    EntityManager entityManager;

    public List<Employee> search( String fName ){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        //From clause
        Root<Employee> root = criteriaQuery.from(Employee.class);

        if(!ObjectUtils.isEmpty(fName)){
            Predicate fNamePredicate = criteriaBuilder.like(root.get("fName"), "%"+fName+"%");

            //Where clause
            criteriaQuery.where(fNamePredicate);
        }

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();


    }

    @Override
    public List<String> searchReturnOnlyNames(String fname) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        //
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root.get("fName"));

        if(!Objects.isNull(fname)){

            Predicate fnamePredicate = criteriaBuilder.like(root.get("fName"), '%' + fname + '%');
            criteriaQuery.where(fnamePredicate);
        }

        TypedQuery<String> typedQuery = entityManager.createQuery(criteriaQuery);
        List<String> resultList = typedQuery.getResultList();

        return resultList;
    }

    @Override
    public List<EmployeeNameAndCityDTO> searchReturnNameAndCity(String fname) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        
        //
        Root<Employee> root = criteriaQuery.from(Employee.class);

        Path<Object> pathFname = root.get("fName");
        Path<Object> pathCity = root.get("city");

        //CriteriaQuery<Object[]> select = criteriaQuery.select(criteriaBuilder.array(pathFName, pathCity));
        criteriaQuery.multiselect(pathFname, pathCity);
        //criteriaQuery.select(criteriaBuilder.construct(EmployeeNameAndCityDTO.class, pathFname, pathCity));

        if(!ObjectUtils.isEmpty(fname)){
            CriteriaQuery<Tuple> fName = criteriaQuery.where(criteriaBuilder.like(root.get("fName"), '%' + fname + '%'));
        }

        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = query.getResultList();

        List<EmployeeNameAndCityDTO> dtoList = new ArrayList<>();

        resultList.forEach(x -> {
            EmployeeNameAndCityDTO dto = new EmployeeNameAndCityDTO();
            dto.setfName(x.get(pathFname).toString());
            dto.setCity(x.get(pathCity).toString());
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Override
    public List<EmployeeNamesPincodeDTO> searchTwoRoots(String fname) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        // This is invalid, lol. criteriaQuery.from(Employee.class, Address.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        employeeRoot.fetch("addresses");

        //with employeeRoot.join, hibernate generates following queries - note JOIN
//        Hibernate: select e1_0.id,e1_0.city,e1_0.f_name,e1_0.l_name from employee e1_0 join address a1_0 on e1_0.id=a1_0.employee_id where e1_0.f_name like ? escape ''
//        Hibernate: select a1_0.employee_id,a1_0.id,a1_0.apartment_name,a1_0.city,a1_0.flat_number,a1_0.pincode from address a1_0 where a1_0.employee_id=?

        //Without employeeRoot.join also, we get right response in API.
        //But hibernate generates queries w/o join
//        Hibernate: select e1_0.id,e1_0.city,e1_0.f_name,e1_0.l_name from employee e1_0 where e1_0.f_name like ? escape ''
//        Hibernate: select a1_0.employee_id,a1_0.id,a1_0.apartment_name,a1_0.city,a1_0.flat_number,a1_0.pincode from address a1_0 where a1_0.employee_id=?





//        Path<Object> fNamePath = employeeRoot.get("fName");
//        Path<Object> lNamePath = employeeRoot.get("lName");
//        Path<Object> addressesPath = employeeRoot.get("addresses");

        //criteriaQuery.multiselect(employeeRoot,addressRoot);
        //Acting as outer join

        if(!ObjectUtils.isEmpty(fname)){
            Predicate fNamePredicate = criteriaBuilder.like(employeeRoot.get("fName"), '%' + fname + '%');
            criteriaQuery.where(fNamePredicate);
        }

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        List<Employee> resultList = query.getResultList();

        List<EmployeeNamesPincodeDTO> dtoList = new ArrayList<>();

        resultList.forEach(x -> {


            EmployeeNamesPincodeDTO dto = new EmployeeNamesPincodeDTO();

            dto.setfName(x.getfName());
            dto.setlName(x.getlName());

            List<Address> addresses = x.getAddresses();

            dto.setPinCode(addresses.get(0).getPincode());

            dtoList.add(dto);

        });

        return dtoList;


    }

    @Override
    public List<EmployeeNamesPincodeDTO> fetchEmployeesByPinCode(String pinCode) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);
        root.join("addresses");
//        root.fetch("addresses");

        criteriaQuery.multiselect(root.get("fName"), root.get("lName"), root.get("addresses").get("pincode"));



        ParameterExpression<String> pinCodeParam = criteriaBuilder.parameter(String.class);

        Predicate equal = criteriaBuilder.equal(root.get("addresses").get("pincode"), pinCodeParam);
        criteriaQuery.where(equal);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(pinCodeParam, pinCode);


        //typedQuery.setParameter(pinCode, pinCode);

        List<Employee> resultList = typedQuery.getResultList();

        List<EmployeeNamesPincodeDTO> dtoList = new ArrayList<>();

        resultList.forEach(x -> {
            EmployeeNamesPincodeDTO dto = new EmployeeNamesPincodeDTO();
            dto.setfName(x.getfName());
            dto.setlName(x.getlName());

            List<Address> addresses = x.getAddresses();
            Address address = addresses.get(0);

            dto.setPinCode(address.getPincode());

            dtoList.add(dto);
        });

        return dtoList;

    }
}
