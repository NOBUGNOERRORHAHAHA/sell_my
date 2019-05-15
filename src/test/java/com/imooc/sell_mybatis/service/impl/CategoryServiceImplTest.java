package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.dataobject.ProductCategory;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());

    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategories = categoryService.findAll();
        Assert.assertNotEquals(0, productCategories.size());


    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        Assert.assertNotEquals(0, productCategories.size());

    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("d", 5);
        int result = categoryService.save(productCategory);
        System.out.println(result);
        Assert.assertNotEquals(0,result);

    }
}