package com.wk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MultipleDatasouceTrancationApp {
    public static void main(String[] args) {
        SpringApplication.run(MultipleDatasouceTrancationApp.class,args);
    }
}