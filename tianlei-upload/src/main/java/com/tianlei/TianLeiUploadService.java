package com.tianlei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TianLeiUploadService {
    public static void main(String[] args) {
        SpringApplication.run(TianLeiUploadService.class, args);
    }
}
