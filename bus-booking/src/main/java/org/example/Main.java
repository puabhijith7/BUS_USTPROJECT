package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalTime;

@SpringBootApplication
@EnableFeignClients
public class Main {
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class,args);
        System.out.println(LocalTime.now());
    }
}