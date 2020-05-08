package com.li.missyou.api.v1;

import com.li.missyou.bo.PageCounter;
import com.li.missyou.core.LocalUser;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.logic.OrderChecker;
import com.li.missyou.model.Order;
import com.li.missyou.service.OrderService;
import com.li.missyou.util.CommonUtil;
import com.li.missyou.vo.OrderIdVO;
import com.li.missyou.vo.OrderSimplifyVo;
import com.li.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private OrderService orderService;

    @Value("${missyou.order.pay-time-limit}")
    private Integer payTimeLimit;

    @PostMapping("")
    @ScopeLevel
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);
        // 下单
        Long oid = orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }

    @GetMapping("/status/unpaid")
    @ScopeLevel
    @SuppressWarnings("unchecked")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0") Integer start,
                                             @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVo.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVo) o).setPeriod(this.payTimeLimit.longValue()));
        return pagingDozer;
    }
}
