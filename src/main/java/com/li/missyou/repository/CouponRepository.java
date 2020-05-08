package com.li.missyou.repository;

import com.li.missyou.model.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c " +
            "join c.categoryList ca " +
            "join Activity a on a.id = c.activityId " +
            "where ca.id = :cid " +
            "and a.startTime < :now " +
            "and a.endTime > :now")
    List<Coupon> findByCategory(Long cid, Date now);

    @Query("select c from Coupon c " +
            "join Activity a on c.activityId = a.id " +
            "where c.wholeStore = :isWhoreStore " +
            "and a.startTime < :now " +
            "and a.endTime > :now")
    List<Coupon> findByWholeStore(Boolean isWhoreStore, Date now);

    //
    @Query("select c from Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where uc.status = 1 " +
            "and u.id = :uid " +
            "and c.startTime < :now " +
            "and c.endTime > :now " +
            "and uc.orderId is null")
    List<Coupon> findMyAvailable(Long uid, Date now);

    //
    @Query("select c from Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where u.id = :uid " +
            "and uc.status = 2 " +
            "and uc.orderId is not null " +
            "and c.startTime < :now " +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(@Param("uid") Long uid, @Param("now") Date now);

    //
    @Query("select c from Coupon c " +
            "join UserCoupon uc " +
            "on c.id = uc.couponId " +
            "join User u " +
            "on u.id = uc.userId " +
            "where u.id = :uid " +
            "and uc.status <> 2 " +
            "and uc.orderId is null " +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(@Param("uid") Long uid, @Param("now") Date now);

}
