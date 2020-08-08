package com.li.missyou.api.v1;

import com.li.missyou.model.Tag;
import com.li.missyou.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Validated
public class TagController {
    // TODO

    @Autowired
    private TagService tagService;

    @ResponseBody
    @RequestMapping("/type/{type}")
    List<Tag> findByType(@PathVariable Integer type) {
        return tagService.findByType(type);
    }
}
