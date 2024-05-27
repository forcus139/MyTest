package com.example.assets.base.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;

/**
 * TODO
 * schema参数动态注入
 * @author GengTianMing
 * @since 2023/02/14 10:56
 **/


@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class SchemaParamsterInterceptorTwo implements Interceptor {
    /** SQL中的占位符 */
    private static final String schemaPlaceholder = "_schemaName";
    private static final String TOP = "top 500";


    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("invocation:\t" + invocation.toString());
        //
        if(invocation.getTarget() instanceof  StatementHandler){
            if("prepare".equals(invocation.getMethod().getName())){
                return invokeStatementHandlerPrepare(invocation);
            }
        }
        return null;
    }

    private Object invokeStatementHandlerPrepare(Invocation invocation) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        log.debug("prepare~~~~~~~~~~~~~~~begin");
        System.out.println(sql);
        if(StringUtils.isNotEmpty(sql) && sql.indexOf(schemaPlaceholder)>-1){
            String adminSchema = "mySchema";
            sql = sql.replaceAll(schemaPlaceholder,adminSchema);
            //通过反射回写
            Field sqlNodeField = boundSql.getClass().getDeclaredField("sql");
            sqlNodeField.setAccessible(true);
            sqlNodeField.set(boundSql,sql);
            log.debug("prepare~~~~~~~~~~~~~~~replace");
            log.debug(sql);
        }

//        if ( sql.indexOf(TOP) < 1 && sql.substring(0, 6).equalsIgnoreCase("SELECT") ){
//            sql = sql.substring(0, 6) +" " + TOP + " " + sql.substring(7, sql.length());
//            Field sqlNodeField = boundSql.getClass().getDeclaredField("sql");
//            sqlNodeField.setAccessible(true);
//            sqlNodeField.set(boundSql,sql);
//            log.debug("prepare~~~~~~~~~~~~~~~replace");
//            log.debug(sql);
//        }


        log.debug("prepare~~~~~~~~~~~~~~~end");


        return invocation.proceed();
    }
}

