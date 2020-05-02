package com.li.missyou.vo;

import com.li.missyou.model.Activity;
import com.li.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVo extends ActivityPureVo {

    private List<CouponPureVo> couponList;

    public ActivityCouponVo(Activity activity) {
        super(activity);

        this.couponList = activity.getCouponList().stream().map(coupon -> {
            CouponPureVo couponPureVo = new CouponPureVo(coupon);
            return couponPureVo;
        }).collect(Collectors.toList());

    }
}
