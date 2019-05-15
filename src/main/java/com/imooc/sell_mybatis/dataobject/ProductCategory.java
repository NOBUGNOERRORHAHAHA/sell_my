package com.imooc.sell_mybatis.dataobject;

import lombok.Data;

@Data
public class ProductCategory {
    /**类目id.*/
    private  Integer categoryId;

    /** 类目名字*/
    private  String categoryName;

    /** 类目编号*/
    private Integer categoryType;


    public ProductCategory() {

    }
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
