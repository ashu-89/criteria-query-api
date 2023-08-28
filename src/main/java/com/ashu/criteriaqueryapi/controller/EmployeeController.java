package com.ashu.criteriaqueryapi.controller;

import com.ashu.criteriaqueryapi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ashu.criteriaqueryapi.service.EmployeeService;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("hello")
    public ResponseEntity<String> hello(){

        return new ResponseEntity<>("hello", HttpStatus.OK);

    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){

        List<Employee> employeeList = employeeService.findAll();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    @GetMapping("/employees-search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam(required = false) String fname){

        List<Employee> employeeList = employeeService.search(fname);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    @GetMapping("/employees-name-search")
    public ResponseEntity<List<String>> searchEmployeesNames(@RequestParam(required = false) String fname){

        List<String> employeeList = employeeService.searchReturnOnlyNames(fname);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }
}
