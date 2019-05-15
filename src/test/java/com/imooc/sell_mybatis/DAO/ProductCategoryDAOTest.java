package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.ProductCategory;
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
public class ProductCategoryDAOTest {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;
    @Test
    public void findByIdTest() {
        ProductCategory productCategory = productCategoryDAO.findById(1);
        System.out.println(productCategory);
    }

    @Test
    //@Transactional
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("d",4);
        int result = productCategoryDAO.save(productCategory);
        System.out.println(result);
        Assert.assertNotEquals(0, result);
    }
    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> productCategories = productCategoryDAO.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategories.size());
    }

    @Test
    public void findAllTest() {
        List<ProductCategory> productCategories = productCategoryDAO.findAll();
        Assert.assertNotEquals(0,productCategories.size());
    }
}