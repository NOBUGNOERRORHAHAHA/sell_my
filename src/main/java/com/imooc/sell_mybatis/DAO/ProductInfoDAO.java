package com.imooc.sell_mybatis.DAO;

import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.dto.CartDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoDAO {
    List<ProductInfo> findByProductStatus(@Param("productStatus") Byte productStatus);

    int save(ProductInfo productInfo);

    ProductInfo findOne(@Param("productId") String productId);

    List<ProductInfo> findAll();

    int decreaseStock(CartDTO cartDTOList);

    int increaseStock(CartDTO cartDTOList);

    //int updateStockByStatus(@Param("productQuantity") Integer productQuantity, @Param("productId") String productId);
}
