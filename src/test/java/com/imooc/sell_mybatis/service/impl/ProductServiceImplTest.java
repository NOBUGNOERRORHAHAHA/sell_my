package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;
    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1");
        System.out.println(productInfo);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        List<ProductInfo> productInfoList = productService.findAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("234");
        productInfo.setProductName("ccc");
        productInfo.setProductPrice(new BigDecimal(4.5));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("BBB");
        productInfo.setProductIcon("ADAwq");
        productInfo.setProductStatus((byte) 0);
        productInfo.setCategoryType(1);
        int result = productService.save(productInfo);
        System.out.println(result);
        Assert.assertNotEquals(0,result);
    }
}