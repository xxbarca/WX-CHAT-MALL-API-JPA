package com.li.missyou.repository;

import com.li.missyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date expiredTime,
                                                               Integer status,
                                                               Long userId,
                                                               Pageable pageable);

}
