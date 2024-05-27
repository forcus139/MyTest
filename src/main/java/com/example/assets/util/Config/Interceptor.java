package com.example.assets.util.Config;

import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/03/09 09:07
 **/
public interface Interceptor {
    /**
     * 执行的拦截方法，Invocation 中可以拿到很多我们需要的属性，比如拦截方法名(getMethod())，方法参数(getArgs())，SQL等
     */
    Object intercept(Invocation var1) throws Throwable;
    /**
     * var1代表要拦截的对象，这里实现自己要拦截对象的逻辑
     */
    Object plugin(Object var1);
    /**
     * 插件属性传递
     */
    void setProperties(Properties var1);
}

