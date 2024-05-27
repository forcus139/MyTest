package com.example.assets.base.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/14 10:27
 **/
@Configuration
public class MybatisInterceptConfig {
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;
    /**
     * mybatis 拦截器注册
     */
    @PostConstruct
    public void addSqlInterceptor() {
        SchemaParamsterInterceptorTwo interceptor = new SchemaParamsterInterceptorTwo();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
