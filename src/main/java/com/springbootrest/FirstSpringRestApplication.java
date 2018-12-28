package com.springbootrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages="com.springbootrest")
public class FirstSpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run( FirstSpringRestApplication.class, args );
    }


}
