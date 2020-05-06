package com.li.missyou.core.money;

import java.math.BigDecimal;

public interface IMoneyDiscount {

    /**
     * @param original 原价
     * @param discount 折扣率
     * */
    BigDecimal discount(BigDecimal original, BigDecimal discount);

}
