package com.li.missyou.service;

import com.li.missyou.core.Enumeration.CouponStatus;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.model.Activity;
import com.li.missyou.model.Coupon;
import com.li.missyou.model.UserCoupon;
import com.li.missyou.repository.ActivityRepository;
import com.li.missyou.repository.CouponRepository;
import com.li.missyou.repository.UserCouponRepository;
import com.li.missyou.util.CommonUtil;
import com.li.missyou.vo.ActivityCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    @Override
    public List<Coupon> getWholeStoreCouponList() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    @Override
    public void collectOneCoupon(Long uid, Long couponId) {
        /**判断传入的优惠券是否存在*/
        couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40003));
        /**优惠券对应的活动是否存在*/
        Activity activity = activityRepository.findByCouponListId(couponId).orElseThrow(() -> new NotFoundException(40010));
        /**判断活动是否过期*/
        Date nowDate = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(nowDate, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            /**优惠券已过期*/
            throw new ParameterException(40005);
        }
        /**已经领取了这张优惠券*/
        userCouponRepository
                .findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((uc) -> {throw new ParameterException(40006);});
        /**插入数据库*/
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(uid)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(nowDate)
                .couponId(couponId).build();
        userCouponRepository.save(userCoupon);
    }
}
