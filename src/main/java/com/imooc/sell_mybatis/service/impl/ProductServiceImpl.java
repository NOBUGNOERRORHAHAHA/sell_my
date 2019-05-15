package com.imooc.sell_mybatis.service.impl;

import com.imooc.sell_mybatis.DAO.ProductInfoDAO;
import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.dto.CartDTO;
import com.imooc.sell_mybatis.enums.ResultEnum;
import com.imooc.sell_mybatis.exception.SellException;
import com.imooc.sell_mybatis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoDAO productInfoDAO;
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDAO.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.findByProductStatus((byte)0);
    }

    @Override
    public List<ProductInfo> findAll() {
        return productInfoDAO.findAll() ;
    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            if (productInfoDAO.findOne(cartDTO.getProductId()) == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            } else if (productInfoDAO.increaseStock(cartDTO) <= 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            if (productInfoDAO.findOne(cartDTO.getProductId()) == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            } else if (productInfoDAO.decreaseStock(cartDTO) <= 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
        }
    }
}
