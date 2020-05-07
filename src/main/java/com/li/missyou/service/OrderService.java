package com.li.missyou.service;

import com.li.missyou.dto.OrderDTO;
import com.li.missyou.logic.OrderChecker;

public interface OrderService {

    /**
     * @param uid: 谁的订单
     * @param orderDTO: 订单数据
     * */
    OrderChecker isOk(Long uid, OrderDTO orderDTO);

}
