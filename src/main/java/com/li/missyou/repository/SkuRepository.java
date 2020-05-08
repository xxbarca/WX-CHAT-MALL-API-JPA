package com.li.missyou.repository;

import com.li.missyou.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {

    List<Sku> findAllByIdIn(List<Long> ids);

    /**
     *  减去库存操作
     * @param quantity: 减掉的数量
     * @param sid: sku的id
     * */
    @Modifying
    @Query("update Sku s set s.stock = s.stock - :quantity " +
            "where s.id = :sid " +
            "and s.stock >= :quantity")
    int reducerStock(Long sid, Long quantity);
}
