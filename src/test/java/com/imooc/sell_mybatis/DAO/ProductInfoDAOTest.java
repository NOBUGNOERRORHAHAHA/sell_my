package com.imooc.sell_mybatis.DAO;

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
public class ProductInfoDAOTest {
    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Test
    @Transactional
    public void saveTest() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("234234");
        productInfo.setProductName("BBB");
        productInfo.setProductPrice(new BigDecimal(4.5));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("BBB");
        productInfo.setProductIcon("ADADA");
        productInfo.setProductStatus((byte) 0);
        productInfo.setCategoryType(2);
        int result = productInfoDAO.save(productInfo);
        System.out.println(result);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> productInfoList = productInfoDAO.findByProductStatus((byte)0);
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findOneTest() throws  Exception {
        ProductInfo productInfo = productInfoDAO.findOne("1");
        System.out.println(productInfo);
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductInfo> productInfoList = productInfoDAO.findAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }


}