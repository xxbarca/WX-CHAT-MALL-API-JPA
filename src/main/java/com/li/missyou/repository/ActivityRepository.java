package com.li.missyou.repository;

import com.li.missyou.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByName(String name);

}
