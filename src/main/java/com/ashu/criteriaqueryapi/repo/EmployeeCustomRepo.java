package com.ashu.criteriaqueryapi.repo;

import com.ashu.criteriaqueryapi.model.Employee;

import java.util.List;

public interface EmployeeCustomRepo {
    public List<Employee> search(String fName);

    List<String> searchReturnOnlyNames(String fname);
}
