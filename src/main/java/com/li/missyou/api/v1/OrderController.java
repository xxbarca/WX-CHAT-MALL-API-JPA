package com.li.missyou.api.v1;

import com.li.missyou.bo.PageCounter;
import com.li.missyou.core.LocalUser;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.dto.OrderDTO;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.logic.OrderChecker;
import com.li.missyou.model.Order;
import com.li.missyou.service.OrderService;
import com.li.missyou.util.CommonUtil;
import com.li.missyou.vo.OrderIdVO;
import com.li.missyou.vo.OrderPureVO;
import com.li.missyou.vo.OrderSimplifyVo;
import com.li.missyou.vo.PagingDozer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private Long payTimeLimit;

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


    /**
     * 根据订单状态查询
     * @param count: 每页数量
     * @param start:
     * @param status: 状态, 0: 全部, 2: 已支付, 3: 已发货 4: 已完成
     *
     * */
    @GetMapping("/by/status/{status}")
    @ScopeLevel
    @SuppressWarnings("unchecked")
    public PagingDozer getByStatus(@PathVariable int status,
                                   @RequestParam(defaultValue = "0") Integer start,
                                 @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = this.orderService.getByStatus(status, page.getPage(), page.getCount());
        PagingDozer pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVo.class);
        pagingDozer.getItems().forEach((o) -> ((OrderSimplifyVo) o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }

    /**
     * 订单详情
     * @param id: 订单id
     * */
    @GetMapping("/detail/{id}")
    @ScopeLevel
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long id) {
        Optional<Order> orderOptional = this.orderService.getOrderDetail(id);
        return orderOptional.map((o) -> new OrderPureVO(o, payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50009));
    }
}
