package com.li.missyou.repository;

import com.li.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByName(String name);

    Optional<Activity> findByCouponListId(Long couponId);

}
