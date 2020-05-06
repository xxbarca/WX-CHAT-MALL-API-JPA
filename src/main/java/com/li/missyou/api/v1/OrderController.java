package com.li.missyou.api.v1;

import com.li.missyou.core.LocalUser;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.model.User;
import com.li.missyou.vo.OrderIdVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 * 校验参数:
 *  1. 商品 是否有货
 *  2. 商品最大购买数量校验, 订单商品总数量限制
 *  3. 每个sku的数量校验, 数量限制
 *  4. totalPrice
 *  5. finalTotalPrice
 *  6. 优惠券校验, 是否拥有这个优惠券
 *  7. 优惠券是否过期
 * */

@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @PostMapping("")
    @ScopeLevel
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        return null;
    }
}
