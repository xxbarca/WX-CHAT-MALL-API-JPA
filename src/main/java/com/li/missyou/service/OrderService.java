package com.li.missyou.service;

import com.li.missyou.dto.OrderDTO;
import com.li.missyou.logic.CouponChecker;
import com.li.missyou.logic.OrderChecker;
import com.li.missyou.model.Order;
import org.jvnet.staxex.BinaryText;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

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

    /**
     * 待支付的订单数量
     * @param page: 页码
     * @param size: 每页数量
     * */
    Page<Order> getUnpaid(Integer page, Integer size);

    /**
     * 根据状态查询订单列表
     * @param page: 页数
     * @param size: 每页数量
     * @param status: 状态
     */
    Page<Order> getByStatus(Integer status, Integer page, Integer size);

    /**
     * 获取订单详情
     * @param id : 订单id
     * */
    Optional<Order> getOrderDetail(Long id);
}
