package com.li.missyou.repository;

import com.li.missyou.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findFirstByUserIdAndCouponIdAndStatus(Long uid, Long couponId, int status);

    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long couponId);

    /**
     * 核销优惠券
     * @param couponId: 优惠券id
     * @param oid: 订单id
     * @param uid: 用户id
     *  1. 改变status
     *  2. orderId -> 订单号
     * */
    @Modifying
    @Query("update UserCoupon uc " +
            "set uc.status = 2, uc.orderId = :oid " +
            "where uc.userId = :uid " +
            "and uc.couponId = :couponId " +
            "and uc.status = 1 " +
            "and uc.orderId is null")
    int writeOff(Long couponId, Long oid, Long uid);
}
