package com.li.missyou.api.v1;

import com.li.missyou.model.SaleExplain;
import com.li.missyou.service.SaleExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/sale_explain")
@RestController
public class SaleExplainController {

    @Autowired
    private SaleExplainService saleExplainService;

    @GetMapping("/fixed")
    public List<SaleExplain> findAll() {
        return this.saleExplainService.findAll();
    }
}
