package com.imooc.sell_mybatis.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell_mybatis.dataobject.OrderDetail;
import com.imooc.sell_mybatis.enums.OrderStatusEnum;
import com.imooc.sell_mybatis.enums.PayStatusEnum;
import com.imooc.sell_mybatis.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenId;

    private BigDecimal orderAmount;

    private Byte orderStatus;

    private Byte payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
