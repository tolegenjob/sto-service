package com.example.stoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoServiceApplication.class, args);
    }

}
