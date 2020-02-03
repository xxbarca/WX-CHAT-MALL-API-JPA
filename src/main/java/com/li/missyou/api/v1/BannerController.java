package com.li.missyou.api.v1;

import com.li.missyou.dto.PersonDTO;
import com.li.missyou.dto.SchoolDTO;
import com.li.missyou.exception.http.ForbiddenException;
import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.sample.IConnect;
import com.li.missyou.sample.ISkill;
import com.li.missyou.sample.hero.Diana;
import com.li.missyou.service.BannerService;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.websocket.server.PathParam;
import java.util.Map;

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

    // bytype(默认)
    // byname
    @Autowired
//    @Qualifier("irealia")
    private ISkill iSkill;

    @Autowired
    private IConnect iConnect;

    @Autowired
    private BannerService bannerService;

    // v1/2?name=7yue
    @GetMapping("/test/{id}")
    public String test(@PathVariable Integer id, @RequestParam String name) throws Exception {
        iSkill.r();
        throw new ForbiddenException(10001);
    }

    /**
     *
     * */
    @PostMapping("/test1/{id}")
    public String test1(@PathVariable @Range(min = 1, max = 10, message = "范围不对哦") Integer id,
                        @RequestParam @Length(min = 8) String name,
                        @RequestBody @Validated PersonDTO person) throws Exception {
        iSkill.r();
        throw new ForbiddenException(10001);
    }
}
