package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Where(clause = "delete_time is null")
public class Coupon extends BaseEntity {

    @Id
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
    // 类型
    private Integer type;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
    private List<Category> categoryList;
}
