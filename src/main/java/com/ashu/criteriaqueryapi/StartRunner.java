package com.ashu.criteriaqueryapi;

import lombok.AllArgsConstructor;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import repo.EmployeeRepo;

import java.time.LocalDate;
import java.util.*;

@Component
@AllArgsConstructor
public class StartRunner implements ApplicationRunner {

    /* Add whatever Bean you need here and autowire them through the constructor or with @Autowired */
    @Autowired
    EmployeeRepo employeeRepo;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Do whatever you need here inside

        Employee ashu = new Employee();
        ashu.setFName("Ashu");
        ashu.setLName("Pat");
        ashu.setCity("Bangalore");

        employeeRepo.save(ashu);



    }


}