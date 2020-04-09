package com.li.missyou.model;

import com.li.missyou.util.ListAndJson;
import com.li.missyou.util.MapAndJson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Sku extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private Long spuId;
    private Long categoryId;
    private Long rootCategoryId;

    // 规格
    @Convert(converter = ListAndJson.class)
    private List<Object> specs;

    private String code;
    private Long stock;

    /**
     * 映射到数据库中的json列
     * */
    @Convert(converter = MapAndJson.class)
    private Map<String, Object> test;

}
