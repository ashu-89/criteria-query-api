package com.ashu.criteriaqueryapi.service;

import com.ashu.criteriaqueryapi.dto.EmployeeNameAndCityDTO;
import com.ashu.criteriaqueryapi.model.Employee;

import com.ashu.criteriaqueryapi.repo.EmployeeCustomRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ashu.criteriaqueryapi.repo.EmployeeRepo;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public List<Employee> findAll() {

        return employeeRepo.findAll();
    }

    public List<Employee> search(String fname) {
        return employeeRepo.search(fname);
    }

    public List<String> searchReturnOnlyNames(String fname) {
        return employeeRepo.searchReturnOnlyNames(fname);
    }

    public List<EmployeeNameAndCityDTO> searchReturnNameAndCity(String fname) {
        return employeeRepo.searchReturnNameAndCity(fname);
    }
}
