package com.li.missyou.api.v1;
import com.li.missyou.core.interceptors.ScopeLevel;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.model.Banner;
import com.li.missyou.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {

    /**
     * 寻找ISkill 的实现bean
     * 1. 找不到任何一个bean 则报错
     * 2. 找到一个那么直接注入
     * 3. 找到多个 并不一定会报错 按照字段名字推断选择那个bean
     * 4. 按照名字和类型都没有找到相应的bean 报错
     * */

    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    @ScopeLevel(value = 4)
    public Banner getByName(@PathVariable @NotBlank String name) {
        Banner banner = bannerService.getByName(name);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }
}
