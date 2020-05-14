package com.li.missyou.api.v1;

import com.li.missyou.model.Sku;
import com.li.missyou.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping("/sku")
    public List<Sku> getSkuListByIds(@RequestParam String ids) {
        String[] split = ids.split(",");
        List<Long> longList = Arrays.stream(split).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        return skuService.getSkuListByIds(longList);
    }
}
