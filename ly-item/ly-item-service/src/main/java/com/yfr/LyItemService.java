package com.yfr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(value = "com.yfr.mapper")
public class LyItemService {
    public static void main(String[] args) {
        SpringApplication.run(LyItemService.class);
    }
}
