package com.li.missyou.service;

import com.li.missyou.dto.OrderDTO;
import com.li.missyou.logic.CouponChecker;
import com.li.missyou.logic.OrderChecker;

public interface OrderService {

    /**
     * 订单校验
     * @param uid: 谁的订单
     * @param orderDTO: 订单数据
     * */
    OrderChecker isOk(Long uid, OrderDTO orderDTO);

    /**
     * 下单
     * @param uid:
     * @param orderDTO:
     * @param orderChecker:
     * */
    Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker);

}
