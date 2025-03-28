package com.wk.local.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.wk.local.message.mapper"})
public class LocalMessageTableApp {
    public static void main(String[] args) {
        SpringApplication.run(LocalMessageTableApp.class,args);
    }
}