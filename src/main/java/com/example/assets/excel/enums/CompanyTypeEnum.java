package com.example.assets.excel.enums;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/12/29 10:49
 **/
/**
 * 公司类型
 */
public enum CompanyTypeEnum {
    GY(1,"国有"),SY(2,"私营");

    private int num;
    private String type;

    CompanyTypeEnum(int num,String type) {
        this.num = num;
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
