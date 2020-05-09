package com.li.missyou.service;

import com.li.missyou.model.SaleExplain;
import com.li.missyou.repository.SaleExplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleExplainImpl implements SaleExplainService {

    @Autowired
    private SaleExplainRepository saleExplainRepository;

    @Override
    public List<SaleExplain> findAll() {
        return this.saleExplainRepository.findAll();
    }
}
