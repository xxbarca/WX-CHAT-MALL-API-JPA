package com.li.missyou.model;

import com.li.missyou.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderSku {

    private Long id;
    // sku 对应的 spuId
    private Long spuId;
    // singlePrice * count
    private BigDecimal finalPrice;
    // 一个sku的单价
    private BigDecimal singlePrice;
    // 规格值
    private List<String> specValues;
    // 购买数量
    private Integer count;
    private String img;
    private String title;

    public OrderSku(SkuInfoDTO skuInfoDTO, Sku sku) {
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValueList();
    }
}
