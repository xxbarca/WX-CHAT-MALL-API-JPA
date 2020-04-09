package com.li.missyou.repository;

import com.li.missyou.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpuRepository extends JpaRepository<Spu, Long> {

    Spu findOneById(Long id);

    /**
     * 查询二级分类
     * */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);

    /**
     * 查询根分类
     * */
    Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Long cid, Pageable pageable);
}
