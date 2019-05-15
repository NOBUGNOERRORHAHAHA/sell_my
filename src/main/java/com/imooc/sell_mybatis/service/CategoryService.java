package com.imooc.sell_mybatis.service;

import com.imooc.sell_mybatis.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne (Integer categoryId);

    List<ProductCategory> findAll();

    /**买家端用到的*/
    List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypeList);
    /**新增和更新*/
    int save (ProductCategory productCategory);
}
