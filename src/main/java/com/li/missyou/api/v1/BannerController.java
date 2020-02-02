package com.li.missyou.api.v1;

import com.li.missyou.exception.http.ForbiddenException;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.sample.IConnect;
import com.li.missyou.sample.ISkill;
import com.li.missyou.sample.hero.Diana;
import com.li.missyou.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
public class BannerController {

    /**
     * 寻找ISkill 的实现bean
     * 1. 找不到任何一个bean 则报错
     * 2. 找到一个那么直接注入
     * 3. 找到多个 并不一定会报错 按照字段名字推断选择那个bean
     * 4. 按照名字和类型都没有找到相应的bean 报错
     * */

    // bytype(默认)
    // byname
    @Autowired
//    @Qualifier("irealia")
    private ISkill iSkill;

    @Autowired
    private IConnect iConnect;

    @Autowired
    private BannerService bannerService;

    @GetMapping("/test")
    public String test() throws Exception {
        throw new ForbiddenException(10001);
    }
}
