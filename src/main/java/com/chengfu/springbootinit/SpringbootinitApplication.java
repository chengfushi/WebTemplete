package com.chengfu.springbootinit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chengfu.springbootinit.mapper")
public class SpringbootinitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootinitApplication.class, args);
    }

}
