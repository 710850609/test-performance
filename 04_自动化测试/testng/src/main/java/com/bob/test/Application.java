package com.bob.test;

import com.bob.test.util.TestNg;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bob.test.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(TestNg.attributes().get("spring.datasource.password"));
        System.out.println(TestNg.attributes().get("spring.datasource.password"));
    }
}
