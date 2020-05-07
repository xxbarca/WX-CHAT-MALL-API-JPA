package com.li.missyou.logic;

import com.li.missyou.bo.SkuOrderBO;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.dto.SkuInfoDTO;
import com.li.missyou.exception.http.ParameterException;
import com.li.missyou.model.Sku;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * */
public class OrderChecker {

    private CouponChecker couponChecker;
    private List<Sku> serverSkuList;
    private OrderDTO orderDTO;
    private Integer maxSkuLimit;

    public OrderChecker(OrderDTO orderDTO, List<Sku> serverSkuList, CouponChecker couponChecker, Integer maxSkuLimit) {
        this.orderDTO = orderDTO;
        this.serverSkuList = serverSkuList;
        this.couponChecker = couponChecker;
        this.maxSkuLimit = maxSkuLimit;
    }

    /**
     * order校验
     * 1. orderTotalPrice 和 serverTotalPrice 校验
     * 2. sku是否下架
     * 3. 是否有售罄的商品
     * 4. 没有售罄, 但购买数量超出库存
     * 5. 优惠券校验
     * */
    public void isOk() {
        // 服务端当前订单原价
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOList = new ArrayList<>();
        //
        this.skuNotOnSale(orderDTO.getSkuInfoList().size(), serverSkuList.size());
        //
        for (int i = 0; i < serverSkuList.size(); i++) {
            Sku sku = serverSkuList.get(i);
            SkuInfoDTO skuInfoDto = orderDTO.getSkuInfoList().get(i);
            //
            this.containsSoldOutSku(sku);
            //
            this.beyondSkuStock(sku, skuInfoDto);
            //
            this.beyondMaxSkuLimit(skuInfoDto);
            // TODO -- ?
            serverTotalPrice.add(this.calculateSkuOrderPrice(sku, skuInfoDto));
            //
            skuOrderBOList.add(new SkuOrderBO(sku, skuInfoDto));
        }
        //
        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
        //
        this.couponCheckerIsOk(skuOrderBOList, serverTotalPrice);
    }

    /**
     * 优惠券校验
     * */
    private void couponCheckerIsOk(List<SkuOrderBO> skuOrderBOList, BigDecimal serverTotalPrice) {
        if (this.couponChecker != null) {
            this.couponChecker.isOk();
            this.couponChecker.canBeUsed(skuOrderBOList, serverTotalPrice);
            this.couponChecker.finalTotalPriceIsOk(this.orderDTO.getFinalTotalPrice(), serverTotalPrice);
        }
    }

    /**
     * 前端价格与服务器计算机个比较
     * @param serverTotalPrice 服务端计算的总原价
     * @param orderTotalPrice 前端传过来的价格
     * */
    private void totalPriceIsOk(BigDecimal orderTotalPrice, BigDecimal serverTotalPrice) {
        if (orderTotalPrice.compareTo(serverTotalPrice) != 0) {
            throw new ParameterException(50005);
        }
    }

    /**
     *
     * @param skuInfoDTO
     * @param sku
     * */
    private BigDecimal calculateSkuOrderPrice(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() <= 0) {
            throw new ParameterException(50007);
        }
        return sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
    }

    /**
     * 检测是否有下架商品
     * @param count1
     * @param count2
     * */
    private void skuNotOnSale(int count1, int count2) {
        if (count1 != count2) {
            throw new ParameterException(50002);
        }
    }

    /**
     * 检测商品是否售罄
     * 初步预判断
     * @param sku
     * */
    private void containsSoldOutSku(Sku sku) {
        if (sku.getStock() == 0) {
            throw new ParameterException(50001);
        }
    }

    /**
     * 检测购买数量是否超出库存
     * @param sku
     * @param skuInfoDTO
     * */
    private void beyondSkuStock(Sku sku, SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > sku.getStock()) {
            throw new ParameterException(50003);
        }
    }

    /**
     * 购买数量是否超出了最大购买数量
     * @param skuInfoDTO
     * */
    private void beyondMaxSkuLimit(SkuInfoDTO skuInfoDTO) {
        if (skuInfoDTO.getCount() > this.maxSkuLimit) {
            throw new ParameterException(50004);
        }
    }

}
