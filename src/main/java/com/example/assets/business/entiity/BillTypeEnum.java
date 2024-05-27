package com.example.assets.business.entiity;

/**
 * @author liuyang
 * @Description: 单据类型枚举
 * @Date: 2021/11/17 14:17
 */
public enum BillTypeEnum {

    SETTLEMENT_ORDER_PREFIX("PSTD", "结算定案单"),
    ACCOUNT_PAYABLE_SETTLEMENT("PSAD", "结算单"),
    EXPENSES_ORDER_PREFIX("PEXD", "费用单"),
    PAYMENY_PREFIX("PAYD", "付款单"),
    ADVANCE_PAYMENY_PREFIX("PAPD", "预付款单"),
    ENG_MAT_RECEIPT_ORDER_PREFIX("PGRD","工程材料入库");

    /**
     * 编码
     */
    private final String code;
    /**
     * 名称
     */
    private final String name;

    BillTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

    public static String getTypeName(String code) {
        for (BillTypeEnum billTypeEnum : BillTypeEnum.values()) {
            if (billTypeEnum.getCode().equals(code)) {
                return billTypeEnum.getName();
            }
        }
        return "";
    }
}
