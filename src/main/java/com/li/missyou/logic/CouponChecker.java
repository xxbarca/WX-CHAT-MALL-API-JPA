package com.li.missyou.logic;

import com.li.missyou.bo.SkuOrderBO;
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
import java.util.List;
import java.util.stream.Collectors;

public class CouponChecker {

    private Coupon coupon;
    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon,  IMoneyDiscount iMoneyDiscount) {
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /**
     * 优惠券是否过期
     * */
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
     * @param serverTotalPrice : 服务端计算的总价格
     *
     * */
    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice) {
        // 服务端计算的最终价格
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_MINUS: /**满减券*/
            case NO_THRESHOLD_MINUS: /**无门槛券*/
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

    /**
     * 核对当前优惠券是否真的能被使用
     * 带有使用分类优惠券的校验
     *
     * */
    public void canBeUsed(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        // 所属分类商品价格总和
        BigDecimal orderCategoryPrice;
        if (this.coupon.getWholeStore()) { /**全场券, 没有分类限制, 不需要按分类进行累加*/
            orderCategoryPrice = serverTotalPrice;
        } else {
            List<Long> cidList = this.coupon.getCategoryList()
                    .stream().map(category -> category.getId())
                    .collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(skuOrderBOList, cidList);
        }
        /**orderCategoryPrice 和 优惠券的满减门槛作比较做比较*/
        this.couponCanBeUsed(orderCategoryPrice);
    }

    /**
     *  所有分类价格总和
     * @param skuOrderBOList
     * @param cidList
     * */

    private BigDecimal getSumByCategoryList(List<SkuOrderBO> skuOrderBOList, List<Long> cidList) {
        return cidList.stream()
                .map(cid -> this.getSumByCategory(skuOrderBOList, cid))
                .reduce(BigDecimal::add).orElse(new BigDecimal("0"));
    }
    /**
     * 一个分类下面所有sku的price的总和
     * @param skuOrderBOList
     * */
    private BigDecimal getSumByCategory(List<SkuOrderBO> skuOrderBOList, Long cid) {
        return skuOrderBOList.stream()
                .filter(sku -> sku.getCategoryId().equals(cid)) /**筛选*/
                .map(bo -> bo.getTotalPrice()) /**获取到一个价格列表*/
                .reduce(BigDecimal::add) /**价格累加*/
                .orElse(new BigDecimal("0")); /**给个默认值*/
    }

    /**
     *  orderCategoryPrice 和 优惠券的满减门槛作比较做比较
     *  @param orderCategoryPrice
     * */
    private void couponCanBeUsed(BigDecimal orderCategoryPrice) {
        switch (CouponType.toType(this.coupon.getType())) {
            case FULL_OFF: /**满减折扣券*/
            case FULL_MINUS: /**满减券*/
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if (compare > 0) {
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS: /**无门槛券*/
                break;

            default:
                throw new ParameterException(40009);
        }
    }

}
