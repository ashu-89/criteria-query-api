package com.ashu.criteriaqueryapi;

import com.ashu.criteriaqueryapi.model.Employee;
import com.ashu.criteriaqueryapi.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
//@AllArgsConstructor
public class StartRunner implements ApplicationRunner {

    /* Add whatever Bean you need here and autowire them through the constructor or with @Autowired */
    @Autowired
    EmployeeRepo employeeRepo;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Do whatever you need here inside

        Employee ashu = new Employee();

        ashu.setfName("Ashu");
        ashu.setlName("Pat");
        ashu.setCity("Bangalore");

        employeeRepo.save(ashu);

        //
        Employee abhi = new Employee();

        abhi.setfName("Abhi");
        abhi.setlName("Pat");
        abhi.setCity("Bangalore");

        employeeRepo.save(abhi);



    }


}