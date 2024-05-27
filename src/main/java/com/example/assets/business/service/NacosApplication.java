//package com.example.assets.business.service;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/02/21 09:16
// **/
//
//@ComponentScan(basePackages = {"com.xiang"})
//@SpringBootApplication
//@CrossOrigin
//@NacosPropertySource(dataId = "xxx", groupId = "xxx", autoRefreshed = true, type = ConfigType.TEXT)
//public class NacosApplication {
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(NacosApplication.class, args);
//        System.out.println("启动成功");
//    }
//
//    static {
//        List<String> list = Arrays.asList(String.valueOf(System.getenv().get("HITENACOS")).split(":"));
//        System.setProperty("nacos.config.server-addr", "127.0.0.1:8848");
//        System.setProperty("nacos.config.namespace", "namespace");
//        System.setProperty("nacos.config.username", "username");
//        System.setProperty("nacos.config.password", "password");
//    }
//
//}