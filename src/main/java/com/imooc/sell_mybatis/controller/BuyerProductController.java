package com.imooc.sell_mybatis.controller;

import com.imooc.sell_mybatis.VO.ProductInfoVO;
import com.imooc.sell_mybatis.VO.ProductVO;
import com.imooc.sell_mybatis.VO.ResultVO;
import com.imooc.sell_mybatis.dataobject.ProductCategory;
import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.service.CategoryService;
import com.imooc.sell_mybatis.service.ProductService;
import com.imooc.sell_mybatis.utils.ResultVOUtil;
import com.imooc.sell_mybatis.VO.ProductInfoVO;
import com.imooc.sell_mybatis.VO.ProductVO;
import com.imooc.sell_mybatis.VO.ResultVO;
import com.imooc.sell_mybatis.dataobject.ProductCategory;
import com.imooc.sell_mybatis.dataobject.ProductInfo;
import com.imooc.sell_mybatis.service.CategoryService;
import com.imooc.sell_mybatis.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResultVO list() {

        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2.查询类目（一次性查询）
        List<Integer> categoryTypeList = new ArrayList<>();

        for (ProductInfo productInfo : productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);


        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory :productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }



        return ResultVOUtil.success(productVOList);
    }
}
