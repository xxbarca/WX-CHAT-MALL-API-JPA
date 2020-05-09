package com.li.missyou.repository;

import com.li.missyou.model.SaleExplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SaleExplainRepository extends JpaRepository<SaleExplain, Long> {
    List<SaleExplain> findAll();
}
