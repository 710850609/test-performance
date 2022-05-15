package com.bob.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.TestNG;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@MapperScan("com.bob.test.dao")
public class Application {

    private static String testngCliKey = "testngCli";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        runTestNg(args);
    }
    public static void runTestNg(String... args)  {
        String testngCl = System.getProperty(testngCliKey, "");
        log.info("读取【{}】testng命令行参数:{}", testngCliKey, testngCl);
        String[] cli = Arrays.stream(testngCl.split(","))
                .filter(e -> StringUtils.isNotBlank(e))
                .toArray(String[]::new);
        if (cli.length > 0) {
            log.info("执行testng命令行： org.testng.TestNG {}", cli);
            TestNG.privateMain(cli, null);
        } else {
            log.info("【{}】未配置，跳过执行testng命令行", testngCliKey);
        }
    }
}
