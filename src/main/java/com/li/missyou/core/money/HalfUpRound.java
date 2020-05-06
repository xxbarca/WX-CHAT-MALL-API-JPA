package com.li.missyou.core.money;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HalfUpRound implements IMoneyDiscount {
    @Override
    public BigDecimal discount(BigDecimal original, BigDecimal discount) {
        BigDecimal actual = original.multiply(discount);
        // 四舍五入, 保留两位小数
        BigDecimal finalMoney = actual.setScale(2, RoundingMode.HALF_UP);
        return actual;
    }
}
