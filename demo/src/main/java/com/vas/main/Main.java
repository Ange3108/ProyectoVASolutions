package com.vas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vas")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}