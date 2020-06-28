package com.rysh.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.rysh.api")
@ComponentScan(basePackages = "com.rysh.module.*.service.impl")
@MapperScan(basePackages = "com.rysh.module.*.mapper")
@EnableScheduling
public class ModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class,args);
    }
}
