package com.li.missyou.repository;

import com.li.missyou.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GridCategoryRepository extends JpaRepository<GridCategory, Long> {
}
