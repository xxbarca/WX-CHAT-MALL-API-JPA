package com.li.missyou.api.v1;

import com.li.missyou.core.LocalUser;
import com.li.missyou.core.UnifyResponse;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.exception.CreateSuccess;
import com.li.missyou.model.Coupon;
import com.li.missyou.model.User;
import com.li.missyou.service.CouponService;
import com.li.missyou.vo.CouponPureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("/coupon")
@RestController
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 商品详情页可用优惠券
     * @param cid: 二级分类id号码
     * */
    @GetMapping("/by/category/{cid}")
    public List<CouponPureVo> getCouponListByCategory(@PathVariable Long cid) {
        List<Coupon> coupons = couponService.getByCategory(cid);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        List<CouponPureVo> vos = CouponPureVo.getList(coupons);
        return vos;
    }


    /**
     * 全场优惠券
     * */
    @GetMapping("/whole_store")
    public List<CouponPureVo> getWholeStoreCouponList() {
        List<Coupon> coupons = couponService.getWholeStoreCouponList();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        List<CouponPureVo> vos = CouponPureVo.getList(coupons);
        return vos;
    }

    /**
     * 领取优惠券, 需要登陆
     * ScopeLevel进行权限控制
     *
     * @param id: 优惠券id
     * */
    @ScopeLevel()
    @PostMapping("/collect/{id}")
    public void collectCoupon(@PathVariable Long id) {
        Long uid = LocalUser.getUser().getId();
        couponService.collectOneCoupon(uid, id);
        UnifyResponse.createSuccess(0);
    }
}
