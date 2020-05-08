package com.li.missyou.service;

import com.li.missyou.core.Enumeration.OrderStatus;
import com.li.missyou.core.money.IMoneyDiscount;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.dto.SkuInfoDTO;
import com.li.missyou.exception.http.ForbiddenException;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.logic.CouponChecker;
import com.li.missyou.logic.OrderChecker;
import com.li.missyou.model.*;
import com.li.missyou.repository.CouponRepository;
import com.li.missyou.repository.OrderRepository;
import com.li.missyou.repository.SkuRepository;
import com.li.missyou.repository.UserCouponRepository;
import com.li.missyou.util.CommonUtil;
import com.li.missyou.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;


    @Value("${missyou.order.max-ku-limit}")
    private Integer maxSkuLimit;

    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Override
    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if (orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ParameterException(50011);
        }

        List<Long> skuIds = orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());
        List<Sku> skuList = skuService.getSkuListByIds(skuIds);

        Long couponId = orderDTO.getCouponId();
        CouponChecker couponChecker = null;
        if (couponId != null) {
            // (coupon == null) => 消费的这张优惠券不存在
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));
            // (coupon != null) && (userCoupon == null) => 优惠券存在但是用户没领取
            UserCoupon userCoupon = userCouponRepository
                    .findFirstByUserIdAndCouponIdAndStatus(uid, couponId, 1)
                    .orElseThrow(() -> new NotFoundException(50006));
            //
            couponChecker = new CouponChecker(coupon, iMoneyDiscount);
        }
        //
        OrderChecker orderChecker = new OrderChecker(orderDTO, skuList, couponChecker, maxSkuLimit);
        //
        orderChecker.isOk();
        //
        return orderChecker;
    }

    /**
     * 处理订单
     * */
    @Override
    @Transactional // 添加事务
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
        Calendar now1 = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();
        Order order = Order.builder().orderNo(orderNo)
                        .totalPrice(orderDTO.getTotalPrice())
                        .finalTotalPrice(orderDTO.getFinalTotalPrice())
                        .userId(uid)
                        .totalCount(orderChecker.getTotalCount().longValue())
                        .snapImg(orderChecker.getLeaderImg())
                        .snapTitle(orderChecker.getLeaderTitle())
                        .status(OrderStatus.UNPAID.value())
                        .expiredTime(expiredTime)
                        .placedTime(now1.getTime())
                        .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        // 保存订单
        this.orderRepository.save(order);
        // 减库存
        this.reduceStock(orderChecker);
        // 核销优惠券
        if (orderDTO.getCouponId() != null) {
            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
        }
        // 数据加入到延迟消息队列
        return order.getId();
    }

    /**
     * 核销优惠券
     * @param couponId: 优惠券id
     * @param orderId: 订单id
     * @param userId: 用户id
     *  1. 改变status
     *  2. orderId -> 订单号
     * */
    private void writeOffCoupon(Long couponId, Long orderId, Long userId) {
        int result = this.userCouponRepository.writeOff(couponId, orderId, userId);
        if (result != 1) {
            throw new ForbiddenException(40002);
        }
    }

    /**
     * 减去库存
     * @param orderChecker
     *  1. 正常
     *  2. 负数
     *  3. 库存不足
     *  4. 加锁 排队
     * */
    private void reduceStock(OrderChecker orderChecker) {
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku orderSku: orderSkuList) {
            int result = this.skuRepository.reducerStock(orderSku.getId(), orderSku.getCount().longValue());
            // 操作一条记录, 如果成功则返回1
            if (result != 1) {
                throw new ParameterException(50003);
            }
        }
    }
}