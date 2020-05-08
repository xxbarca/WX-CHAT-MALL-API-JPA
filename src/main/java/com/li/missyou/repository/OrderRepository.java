package com.li.missyou.repository;

import com.li.missyou.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date expiredTime,
                                                               Integer status,
                                                               Long userId,
                                                               Pageable pageable);

    Page<Order> findByUserId(Long uid, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long uid, Integer status, Pageable pageable);

    Optional<Order> findFirstByUserIdAndId(Long uid, Long id);

}
