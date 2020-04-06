package com.li.missyou.service;

import com.li.missyou.model.Spu;
import com.li.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Spu> getLatestPagingSpu() {
        return this.spuRepository.findAll();
    }
}
