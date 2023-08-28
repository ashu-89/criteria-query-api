package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
}
