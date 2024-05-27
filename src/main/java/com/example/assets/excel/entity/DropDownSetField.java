package com.example.assets.excel.entity;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 10:53
 **/
import java.lang.annotation.*;

/**
 * 注解:自定义标记导出excel的下拉数据集
 */
@Documented
// 作用在字段上
@Target(ElementType.FIELD)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface DropDownSetField {
    // 固定下拉内容
    String[] source() default {};

    // 注解内的名称，解析时要注意对应
    String name() default "";
}

