package com.ashu.criteriaqueryapi.service;

import com.ashu.criteriaqueryapi.model.Employee;

import com.ashu.criteriaqueryapi.repo.EmployeeCustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ashu.criteriaqueryapi.repo.EmployeeRepo;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeCustomRepo employeeCustomRepo;


    public List<Employee> findAll() {

        return employeeRepo.findAll();
    }

    public List<Employee> search(String fname) {

        return employeeCustomRepo.search(fname);
    }
}
