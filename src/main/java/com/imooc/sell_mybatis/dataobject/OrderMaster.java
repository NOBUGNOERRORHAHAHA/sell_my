package com.imooc.sell_mybatis.dataobject;

import com.imooc.sell_mybatis.enums.OrderStatusEnum;
import com.imooc.sell_mybatis.enums.PayStatusEnum;
import lombok.Data;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderMaster {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenId;

    private BigDecimal orderAmount;

    private Byte orderStatus = OrderStatusEnum.NEW.getCode();

    private Byte payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;


}
