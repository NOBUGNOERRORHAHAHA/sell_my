package com.imooc.sell_mybatis.enums;

public enum OrderStatusEnum {

    NEW((byte)0, "新订单"),
    FINISHED((byte)1, "完结"),
    CANCEL((byte)2,"已取消"),
    ;

    private Byte code;

    private String message;

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    OrderStatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
