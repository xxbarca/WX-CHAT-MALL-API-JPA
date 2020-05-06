package com.li.missyou.service;

import com.li.missyou.model.Sku;

import java.util.List;

public interface SkuService {

    List<Sku> getSkuListByIds(List<Long> ids);

}
