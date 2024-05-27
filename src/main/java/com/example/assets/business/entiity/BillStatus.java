package com.example.assets.business.entiity;

/**
 * TODO
 *
 * @author GengTianMing
 * @since 2023/01/10 16:44
 **/
public enum BillStatus {
    ONE("0", "草稿"),
    TWO("1", "审核中"),
    THREE("2", "有效"),
    FOUR("3", "作废"),
    FIVE("4", "完成");

    private String code;

    private String name;

    BillStatus(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(String code){
        return code;
    }

    public String getName(String name){
        return name;
    }

    public static String getStatusName(String code){
        for(BillStatus billStatus : BillStatus.values()){
            if (code.equals(billStatus.code)){
                return billStatus.name;
            }
        }
        return "";
    }
}
