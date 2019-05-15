package com.imooc.sell_mybatis.dataobject;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    /** 商品状态 0正常1下架*/
    private Byte ProductStatus;

    private Integer categoryType;

}
