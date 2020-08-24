package com.tianlei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TianleiRegistry {
    public static void main(String[] args) {
        SpringApplication.run(TianleiRegistry.class, args);
    }
}
