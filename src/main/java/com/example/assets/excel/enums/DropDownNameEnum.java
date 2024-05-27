package com.example.assets.excel.enums;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 10:50
 **/
/**
 * excel模板下拉框名称枚举
 */
public enum DropDownNameEnum {
    COMPANY_TYPE_ENUM("Drop001","企业类型"),
    Pay_TYPE_ENUM("Drop002","缴费状态"),
    RENT_CYCLE_ENUM("Drop003","租金结算周期");

    private String code;
    private String name;

    private DropDownNameEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}

