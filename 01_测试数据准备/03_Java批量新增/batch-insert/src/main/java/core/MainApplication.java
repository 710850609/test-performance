package core;

import core.table.AccountInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Autowired
    private AccountInsert accountInsert;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("启动完成");
        accountInsert.insert(3);
    }
}