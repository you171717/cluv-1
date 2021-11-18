package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;

@RestController
@SpringBootApplication
public class shopApplication {
//    private EntityManagerFactory entityManagerFactory;
    public static void main(String[] args) {
        SpringApplication.run(shopApplication.class, args);
    }
}