package com.li.missyou.service;

import com.li.missyou.core.money.IMoneyDiscount;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.dto.SkuInfoDTO;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.logic.CouponChecker;
import com.li.missyou.logic.OrderChecker;
import com.li.missyou.model.Coupon;
import com.li.missyou.model.Sku;
import com.li.missyou.model.UserCoupon;
import com.li.missyou.repository.CouponRepository;
import com.li.missyou.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Value("${missyou.order.max-ku-limit}")
    private Integer maxSkuLimit;

//    @Value("${missyou.order.pay-time-limit}")
//    private Integer payTimeLimit;

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
            /**
             * (coupon == null) => 消费的这张优惠券不存在
             * */
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new NotFoundException(40004));
            /**
             * (coupon != null) && (userCoupon == null) => 优惠券存在但是用户没领取
             * */
            UserCoupon userCoupon = userCouponRepository
                    .findFirstByUserIdAndCouponId(uid, couponId)
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
}
