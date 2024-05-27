package com.example.assets.base.config;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/14 16:45
 **/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //开启mybatis事物管理
public class MybatisPlusBatchConfig {

    @Bean
    @Primary        //批量插入配置
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }


}
