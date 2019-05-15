package com.imooc.sell_mybatis.enums;


public enum PayStatusEnum {

    WAIT((byte)0, "等待支付"),
    SUCCESS((byte)1,"支付成功"),
    ;
    private Byte code;

    private String message;

    public Byte getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    PayStatusEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
