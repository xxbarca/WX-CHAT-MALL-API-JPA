package com.li.missyou.api.v1;

import com.li.missyou.core.Enumeration.CouponStatus;
import com.li.missyou.core.LocalUser;
import com.li.missyou.core.UnifyResponse;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.model.Coupon;
import com.li.missyou.model.User;
import com.li.missyou.service.CouponService;
import com.li.missyou.vo.CouponCategoryVO;
import com.li.missyou.vo.CouponPureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return CouponPureVo.getList(coupons);
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
        return CouponPureVo.getList(coupons);
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

    /**
     * 获取我的优惠券
     * @param status 1: 可用 2: 已使用 3: 过期
     *
     * */
    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVo> getMyCouponStatus(@PathVariable Integer status) {
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;
        switch (CouponStatus.toType(status)) {
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USER:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        return CouponPureVo.getList(couponList);
    }

    /**
     * 我可用的优惠券, 带分类数据
     * */
    @ScopeLevel
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory() {

        User user = LocalUser.getUser();
        List<Coupon> coupons = couponService.getMyAvailableCoupons(user.getId());
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(coupon -> {
            CouponCategoryVO vo = new CouponCategoryVO(coupon);
            return vo;
        }).collect(Collectors.toList());
    }
}
