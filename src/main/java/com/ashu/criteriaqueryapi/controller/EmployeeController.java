package com.ashu.criteriaqueryapi.controller;

import com.ashu.criteriaqueryapi.dto.EmployeeNameAndCityDTO;
import com.ashu.criteriaqueryapi.dto.EmployeeNamesPincodeDTO;
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
    public ResponseEntity<List<String>> searchEmployeesReturnOnlyNames(@RequestParam(required = false) String fname){

        List<String> employeeList = employeeService.searchReturnOnlyNames(fname);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    @GetMapping("/employees-name-city-search")
    public ResponseEntity<List<EmployeeNameAndCityDTO>> searchEmployeesReturnNameAndCity(@RequestParam(required = false) String fname){

        List<EmployeeNameAndCityDTO> employeeList = employeeService.searchReturnNameAndCity(fname);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    /*

    Idea is to make a criteria query api to join from 2 roots.

    The following native query should be executed using criteria query api.

        select e.f_name, e.l_name, a.pincode
        from employee e inner join Address a on e.id = a.employee_id
        where e.f_name like '%a%';

     */
    @GetMapping("/employees-search-two-roots")
    public ResponseEntity<List<EmployeeNamesPincodeDTO>> searchEmployeesTwoRoots(@RequestParam(required = false) String fname){

        List<EmployeeNamesPincodeDTO> employeeList = employeeService.searchTwoRoots(fname);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }

    /*
    The idea is to pass ID as pathvariable or query parameter and replace placeholder parameter in repo's query with
    the id coming in dynamically at run time.

    //Bind the parameters using criteria query api.

    //We will fetch employee name from employee table where we pass pincode as query paramter and look for pincode in
    address table.
     */

    @GetMapping("/employees-by-pin-code")
    public ResponseEntity<List<EmployeeNamesPincodeDTO>> searchEmployeesById(@RequestParam(required = false) String pinCode){

        List<EmployeeNamesPincodeDTO> employeeList = employeeService.fetchEmployeesByPinCode(pinCode);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);

    }


}
