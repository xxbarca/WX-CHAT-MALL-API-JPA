package com.li.missyou.vo;

import com.li.missyou.model.Coupon;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CouponPureVo {
    private Long id;

    // 外键 活动之间的联系
    private Long activityId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    // 满减券 满1000减200， fullMoney -> 1000
    private BigDecimal fullMoney;
    // 满减券 满1000减200， minus -> 200
    private BigDecimal minus;
    // 折扣券 满1000 打五折 rate -> 五折
    private BigDecimal rate;
    // 对于优惠券的说明, 女装, 牛仔裤, 一种标识
    private String remark;
    // 是否是全场券
    private Boolean wholeStore;
    //
    private Integer type;

    public CouponPureVo(Coupon coupon) {
        BeanUtils.copyProperties(coupon, this);
    }

    public static List<CouponPureVo> getList(List<Coupon> coupons) {
        return coupons.stream().map(CouponPureVo::new).collect(Collectors.toList());
    }
}
