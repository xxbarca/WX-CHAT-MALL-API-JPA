package com.li.missyou.service;

import com.li.missyou.dto.OrderDTO;

public interface OrderService {

    /**
     * @param uid: 谁的订单
     * @param orderDTO: 订单数据
     * */
    void isOk(Long uid, OrderDTO orderDTO);

}
