package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductCategoryDAO {
    ProductCategory findById(@Param("categoryId") Integer categoryId);

    int save(ProductCategory productCategory );

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(@Param("categoryTypeList") List<Integer> categoryTypeList);

}
