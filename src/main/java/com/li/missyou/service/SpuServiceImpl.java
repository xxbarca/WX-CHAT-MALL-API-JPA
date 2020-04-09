package com.li.missyou.service;

import com.li.missyou.model.Spu;
import com.li.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu getSpu(Long id) {
        return this.spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getLatestPagingSpu(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createTime").descending());
        return this.spuRepository.findAll(pageable);
    }

    @Override
    public Page<Spu> getByCategory(Long cid, Boolean isRoot, Integer pageNum, Integer size) {
        Pageable page = PageRequest.of(pageNum, size);
        if (isRoot) {
            return this.spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(cid, page);
        } else {
            return this.spuRepository.findByCategoryIdOrderByCreateTimeDesc(cid, page);
        }
    }
}
