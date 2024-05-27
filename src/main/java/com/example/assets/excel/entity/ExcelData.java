package com.example.assets.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 08:49
 **/
@Data // Lombok库的注解，用于生成getter和setter等方法
public class ExcelData {
    @ColumnWidth(20)
    @ExcelProperty("学生编号")
    private Integer sno;

    @ColumnWidth(20)
    @ExcelProperty("学生姓名")
    private String sname;
}