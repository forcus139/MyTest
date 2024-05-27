package com.example.assets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

//@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@MapperScan("com.example.assets.base.**.mapper")
//@EnableScheduling
//@EnableJpaAuditing
@SpringBootApplication
//@MapperScan(basePackages = {"com.example.assets.base.mapper"})
@MapperScan("com.example.**.mapper")
//@EnableFeignClients(basePackages = {"com.example.assets.business.rpcclient"})
public class AssetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetsApplication.class, args);
    }

}
