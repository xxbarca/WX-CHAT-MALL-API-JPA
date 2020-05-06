package com.li.missyou.logic;

import com.li.missyou.core.Enumeration.CouponType;
import com.li.missyou.core.money.IMoneyDiscount;
import com.li.missyou.exception.http.ForbiddenException;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.model.Coupon;
import com.li.missyou.model.UserCoupon;
import com.li.missyou.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

public class CouponChecker {

    private Coupon coupon;
    private UserCoupon userCoupon;

    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon, IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.userCoupon = userCoupon;
        this.iMoneyDiscount = iMoneyDiscount;

    }

    public void isOk() {
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLine(now, this.coupon.getStartTime(), this.coupon.getEndTime());
        if (!isInTimeLine) {
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 订单最终成交价格是否正确
     * @param orderFinalTotalPrice : 前端计算的最终价格
     * @param serverTotalPrice : 服务端计算的原价
     *
     * */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        // 服务端计算的最终价格
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS: /**满减券*/
            case NO_THRESHOLD_MINUS: /**无门槛折扣券*/
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0")) <= 0) {
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF: /**满减折扣券*/
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
        // 订单价格与最终计算价格比较
        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
        if (compare != 0) {
            throw new ForbiddenException(50008);
        }
    }

    // 核对当前优惠券是否真的能被使用
    public void canBeUsed() {}

}
