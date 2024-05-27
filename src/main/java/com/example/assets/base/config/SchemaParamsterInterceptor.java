package com.example.assets.base.config;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/02/14 10:26
 **/
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class SchemaParamsterInterceptor implements Interceptor {

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
        // 拦截 Executor 的 query 方法 生成sql前将 任意参数 设置到实体中
        if (invocation.getTarget() instanceof Executor ) {
            //&& "query".equals(invocation.getMethod().getName())
            return invokeQuery(invocation);
        }
        return null;
    }

    /** 获取原始sql */
    private String getRawSQL(Invocation invocation) throws NoSuchFieldException, IllegalAccessException {
        //反射获取 SqlSource 对象，通过此对象获取原始SQL
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        /** SqlSource{@link org.apache.ibatis.mapping.SqlSource}的实现类比较多，不方便在所有实现类中解析原始SQL*/
        SqlSource sqlSource = ms.getSqlSource();
        //通过MappedStatement.SqlSource对象获取原生sql不太靠谱！！！
        if(sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) ms.getSqlSource();
            if (dynamicSqlSource == null){
                return null;
            }
            //反射获取 TextSqlNode 对象
            Field sqlNodeField = dynamicSqlSource.getClass().getDeclaredField("rootSqlNode");
            sqlNodeField.setAccessible(true);
            TextSqlNode rootSqlNode = (TextSqlNode) sqlNodeField.get(dynamicSqlSource);
            //反射获取原生sql
            Field textField = rootSqlNode.getClass().getDeclaredField("text");
            textField.setAccessible(true);
            String sql = String.valueOf(textField.get(rootSqlNode));
            return sql;
        }
        if(sqlSource instanceof RawSqlSource) {
            RawSqlSource rawSqlSource = (RawSqlSource) ms.getSqlSource();
            if (rawSqlSource == null){
                return null;
            }
            //反射获取 TextSqlNode 对象
            Field sqlSourceField = rawSqlSource.getClass().getDeclaredField("sqlSource");
            sqlSourceField.setAccessible(true);
            StaticSqlSource staticSqlSource = (StaticSqlSource) sqlSourceField.get(rawSqlSource);
            //反射获取原生sql
            Field sqlField = staticSqlSource.getClass().getDeclaredField("sql");
            sqlField.setAccessible(true);
            String sql = String.valueOf(sqlField.get(staticSqlSource));
            return sql;
        }
        return null;
    }

    private Object invokeQuery(Invocation invocation) throws Exception {
        //todo 按需添加注入參數提高性能
//        String sql = getRawSQL(invocation);
//        if(StringUtils.isBlank(sql) || sql.indexOf(schemaParamsPlaceholder)==-1)
//            return null;

        Executor executor = (Executor) invocation.getTarget();

        // 获取第一个参数
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        // mybatis的参数对象
        Object paramObj = invocation.getArgs()[1];
        if (paramObj == null) {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            paramObj = param;
        }
        //执行脚本
        processParam(paramObj);
        System.out.println("SQL:\t" + paramObj.toString());

        RowBounds rowBounds = (RowBounds)invocation.getArgs()[2];
        ResultHandler resultHandler  = (ResultHandler)invocation.getArgs()[3];
        return executor.query(ms, paramObj,rowBounds,resultHandler);
    }
    /** 处理参数对象 */
    private void processParam(Object parameterObject) throws IllegalAccessException, InvocationTargetException {
        //如果是map且map的key中没有需要的参数，则添加到参数map中
        if (parameterObject instanceof Map) {
            String schemaName = "mySchema";
            ((Map) parameterObject).putIfAbsent("schemaName", schemaName);
            return;
        }
    }
}

