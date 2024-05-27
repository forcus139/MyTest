package com.example.assets.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCondition {
    @ApiModelProperty(name="dwobject", dataType = "varchar", value = "窗口对象", position = 2)
    private String dwobject;

    @ApiModelProperty(name="qryno", dataType = "tinyint", value = "顺序", position = 3)
    private Integer qryno;

    @ApiModelProperty(name="leftpar", dataType = "varchar", value = "左括号", position = 4)
    private String leftpar;

    @ApiModelProperty(name="tabname", dataType = "varchar", value = "表名", position = 5)
    private String tabname;

    @ApiModelProperty(name="coleng", dataType = "varchar", value = "字段名", position = 6)
    private String coleng;

    @ApiModelProperty(name="colchn", dataType = "varchar", value = "中文名", position = 7)
    private String colchn;

    @ApiModelProperty(name="coltype", dataType = "varchar", value = "字段类型", position = 8)
    private String coltype;

    @ApiModelProperty(name="operator", dataType = "varchar", value = "操作符", position = 9)
    private String operator;

    @ApiModelProperty(name="realvalues", dataType = "varchar", value = "操作内容", position = 10)
    private String realvalues;

    @ApiModelProperty(name="rightpar", dataType = "varchar", value = "右括号", position = 11)
    private String rightpar;

    @ApiModelProperty(name="relation", dataType = "varchar", value = "逻辑关系", position = 12)
    private String relation;
}
