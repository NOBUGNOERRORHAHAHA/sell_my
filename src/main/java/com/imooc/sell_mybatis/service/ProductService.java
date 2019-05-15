package com.imooc.sell_mybatis.service;

import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.dto.CartDTO;

import java.util.List;

public interface ProductService {

    ProductInfo findOne( String productId);

    /**查询所有在架商品*/
    List<ProductInfo> findUpAll();

    List<ProductInfo> findAll();

    int save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
