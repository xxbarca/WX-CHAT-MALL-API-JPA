package com.li.missyou.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * Banner(一) BannerItem(多) 一对多关系
 * JPA 标示关系
 * */

@Entity
@Getter
@Setter
public class BannerItem extends BaseEntity {
    @Id
    private Long id;
    private String img;
    private String keyword;
    private short type;
    private Long bannerId;
    private String name;
}
