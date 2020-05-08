package com.li.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.li.missyou.dto.OrderAddressDTO;
import com.li.missyou.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private String snapImg;
    private String snapTitle;
    // 过期时间
    private Date expiredTime;
    // 下单时间
    private Date placedTime;

    /**保存当前订单里面的所有要购买的sku信息*/
    private String snapItems;

    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    //
    public void setSnapItems(List<OrderSku> orderSkus) {
        if (orderSkus.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.objectToJson(orderSkus);
    }

    public List<OrderSku> getSnapItems() {
        return GenericAndJson.jsonToObject(this.snapItems, new TypeReference<List<OrderSku>>() {
        });
    }

    //
    public OrderAddressDTO getSnapAddress() {
        if (this.snapAddress == null) {
            return null;
        }
        return GenericAndJson.jsonToObject(this.snapAddress,
                new TypeReference<OrderAddressDTO>() {
                });
    }
    //
    public void setSnapAddress(OrderAddressDTO address) {
        this.snapAddress = GenericAndJson.objectToJson(address);
    }
}
