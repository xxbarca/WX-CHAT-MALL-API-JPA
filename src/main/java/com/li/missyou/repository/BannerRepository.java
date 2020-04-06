package com.li.missyou.repository;

import com.li.missyou.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @param {Banner} 操作类
 * @param {Long} 操作类主键类型
 * */
public interface BannerRepository extends JpaRepository<Banner, Long> {

    Banner findOneById(Long id);

    Banner findOneByName(String name);

}
