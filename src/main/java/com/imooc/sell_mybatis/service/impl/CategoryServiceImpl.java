package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.DAO.ProductCategoryDAO;
import com.imooc.sell_mybatis.dataobject.ProductCategory;
import com.imooc.sell_mybatis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryDAO.findById(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDAO.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDAO.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public int save(ProductCategory productCategory) {
        return productCategoryDAO.save(productCategory);
    }
}
