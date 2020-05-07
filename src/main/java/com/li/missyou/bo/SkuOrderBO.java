package com.li.missyou.bo;

import com.li.missyou.dto.SkuInfoDTO;
import com.li.missyou.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 用来描述order里面的sku
 * */
@Setter
@Getter
public class SkuOrderBO {

    // 价格
    private BigDecimal actualPrice;

    // 数量
    private Integer count;

    // 分类
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return this.actualPrice.multiply(new BigDecimal(this.count));
    }
}
