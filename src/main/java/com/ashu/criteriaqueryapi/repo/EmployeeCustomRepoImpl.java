package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.dto.EmployeeNameAndCityDTO;
import com.ashu.criteriaqueryapi.model.Employee;
import jakarta.persistence.EntityManager;
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
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        
        //
        Root<Employee> root = criteriaQuery.from(Employee.class);

        Path<Object> pathFName = root.get("fName");
        Path<Object> pathCity = root.get("city");

        CriteriaQuery<Object[]> select = criteriaQuery.select(criteriaBuilder.array(pathFName, pathCity));

        if(!ObjectUtils.isEmpty(fname)){
            CriteriaQuery<Object[]> fName = criteriaQuery.where(criteriaBuilder.like(root.get("fName"), '%' + fname + '%'));
        }

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> resultList = query.getResultList();

        List<EmployeeNameAndCityDTO> namesAndCities = new ArrayList<>();

        resultList.forEach(x -> {
            EmployeeNameAndCityDTO dto = new EmployeeNameAndCityDTO();

            dto.setfName(x[0].toString());
            dto.setCity(x[1].toString());

            namesAndCities.add(dto);

        });

        return namesAndCities;
    }
}
