package com.echo.leaveapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)   // disabling security for testing purpose
@SpringBootApplication
public class LeaveApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeaveApplication.class, args);
    }

}
