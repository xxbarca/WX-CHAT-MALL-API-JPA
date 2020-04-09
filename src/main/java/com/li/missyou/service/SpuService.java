package com.li.missyou.service;

import com.li.missyou.model.Spu;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpuService {

    Spu getSpu(Long id);

    Page<Spu> getLatestPagingSpu(Integer pageNum, Integer pageSize);

    Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer size);
}
