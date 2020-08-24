package com.tianlei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.tianlei.item.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class TianleiItemService {
    public static void main(String[] args) {
        SpringApplication.run(TianleiItemService.class, args);
    }
}
