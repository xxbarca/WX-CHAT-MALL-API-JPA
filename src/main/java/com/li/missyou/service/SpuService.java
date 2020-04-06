package com.li.missyou.service;

import com.li.missyou.model.Spu;

import java.util.List;

public interface SpuService {

    Spu getSpu(Long id);

    List<Spu> getLatestPagingSpu();

}
