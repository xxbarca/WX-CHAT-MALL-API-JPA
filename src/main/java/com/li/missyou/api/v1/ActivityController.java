package com.li.missyou.api.v1;

import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.model.Activity;
import com.li.missyou.service.ActivityService;
import com.li.missyou.vo.ActivityCouponVo;
import com.li.missyou.vo.ActivityPureVo;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/activity")
@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVo getHomeActivity(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }

        ActivityPureVo activityPureVo = new ActivityPureVo(activity);

        return activityPureVo;
    }

    /**
     * 点击活动进入的优惠券列表界面
     * */
    @GetMapping("/name/{name}/with_coupon")
    public ActivityCouponVo getActivityWithCoupon(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }

        ActivityCouponVo activityCouponVo = new ActivityCouponVo(activity);
        return activityCouponVo;

    }
}