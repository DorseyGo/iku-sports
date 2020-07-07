package com.iku.sports.mini.admin.constant;

/**
 * 订单类型，即是课程订单还是商品订单
 */
public enum OrderTypeEnum {
    COURSE(0, "课程"),
    GOODS(1, "商品")
    ;

    private int code;
    private String desc;

    OrderTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
