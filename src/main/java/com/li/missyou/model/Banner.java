package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 写实体类, 自动变成数据表
 * 一对多第三张表: 维护两张表之间的关系
 * */

@Entity
@Getter
@Setter
//where delete_time == null
@Where(clause = "delete_time is null ")
public class Banner extends BaseEntity {
    @Id
    private Long id;
    @Column(length = 16)
    private String name;
    private String description;
    private String title;
    private String img;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="bannerId")
    private List<BannerItem> items;
}
