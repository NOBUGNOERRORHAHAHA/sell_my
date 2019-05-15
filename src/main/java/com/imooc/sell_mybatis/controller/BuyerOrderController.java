package com.imooc.sell_mybatis.controller;

import com.imooc.sell_mybatis.VO.ResultVO;
import com.imooc.sell_mybatis.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell_mybatis.dto.OrderDTO;
import com.imooc.sell_mybatis.enums.ResultEnum;
import com.imooc.sell_mybatis.exception.SellException;
import com.imooc.sell_mybatis.form.OrderForm;
import com.imooc.sell_mybatis.service.BuyerService;
import com.imooc.sell_mybatis.service.OrderService;
import com.imooc.sell_mybatis.utils.ResultVOUtil;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("/create")
    //创建订单
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bingingResult) {
        if (bingingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm = {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                                     bingingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
       if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
       }
       OrderDTO createResult =  orderService.create(orderDTO);
       Map<String, String> map = new HashMap<>();
       map.put("orderId", createResult.getOrderId());

       return ResultVOUtil.success(map);

    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        List<OrderDTO> orderDTOList = orderService.findList(openid);

        return ResultVOUtil.success(orderDTOList);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
