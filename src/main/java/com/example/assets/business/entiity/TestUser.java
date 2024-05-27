package com.example.assets.business.entiity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/01/14 09:07
 **/
@Data
public class TestUser {
    @JSONField(ordinal = 1)
    @ColumnWidth(20)
    @ExcelProperty(value = "用户编号")
//    @ExcelProperty(value = "年龄", fontSize = 14, alignment = Alignment.RIGHT)
    private String userid;

    @JSONField(ordinal = 2)
    @ColumnWidth(20)
    @ExcelProperty("用户名称")
    private String userName;

    @JSONField(ordinal = 3)
    @ColumnWidth(20)
    @ExcelProperty("密码")
    private String passWord;

    @JSONField(ordinal = 4)
    @ColumnWidth(20)
    @ExcelProperty("性别")
    private String sex;

    @JSONField(ordinal = 5)
    @ColumnWidth(20)
    @ExcelProperty("电话号码")
    private String telNo;

    @JSONField(ordinal = 6)
//    @ColumnWidth(20)
    @ExcelProperty("年龄")
    private Integer age;

    @JSONField(ordinal = 7)
//    @ColumnWidth(20)
    @ExcelProperty("体重")
    private Double weight;

//    @JSONField(ordinal = 8, format = "yyyy-MM-dd HH:mm:ss SSS")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @ColumnWidth(20)
    @ExcelProperty("创建时间")
    private Date createTime;

    public TestUser(String userid, String userName, String passWord, String sex,
                    String telNo, Integer age, Double weight, Date createTime){
        this.userid = userid;
        this.userName = userName;
        this.passWord = passWord;
        this.sex = sex;
        this.telNo = telNo;
        this.age = age;
        this.weight = weight;
        this.createTime = createTime;
    }
}
