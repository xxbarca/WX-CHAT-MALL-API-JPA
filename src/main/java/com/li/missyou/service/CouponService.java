package com.li.missyou.service;

import com.li.missyou.model.Coupon;

import java.util.List;

public interface CouponService {

    List<Coupon> getByCategory(Long cid);

    List<Coupon> getWholeStoreCouponList();

    void collectOneCoupon(Long uid, Long couponId);
}
