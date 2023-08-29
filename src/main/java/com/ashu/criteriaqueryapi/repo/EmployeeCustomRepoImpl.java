package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.dto.EmployeeNameAndCityDTO;
import com.ashu.criteriaqueryapi.dto.EmployeeNamesPincodeDTO;
import com.ashu.criteriaqueryapi.model.Address;
import com.ashu.criteriaqueryapi.model.Employee;
import jakarta.persistence.EntityManager;
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
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);

        // This is invalid, lol. criteriaQuery.from(Employee.class, Address.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        criteriaQuery.multiselect(employeeRoot,addressRoot);
        //Acting as outer join

        if(!ObjectUtils.isEmpty(fname)){
            Predicate fNamePredicate = criteriaBuilder.like(employeeRoot.get("fName"), '%' + fname + '%');
            criteriaQuery.where(fNamePredicate);
        }

        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
        List<Tuple> resultList = query.getResultList();

        List<EmployeeNamesPincodeDTO> dtoList = new ArrayList<>();

        resultList.forEach(x -> {
            Employee e = (Employee) x.get(0);
            Address a = (Address) x.get(1);

            EmployeeNamesPincodeDTO dto = new EmployeeNamesPincodeDTO();

            dto.setfName(e.getfName());
            dto.setlName(e.getlName());
            dto.setPinCode(a.getPincode());

            dtoList.add(dto);

        });

        return dtoList;


    }
}
