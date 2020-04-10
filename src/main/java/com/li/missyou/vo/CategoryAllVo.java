package com.li.missyou.vo;

import com.li.missyou.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryAllVo {

    private List<CategoryPureVO> roots;
    private List<CategoryPureVO> subs;

    public CategoryAllVo(Map<Integer, List<Category>> map) {
        this.roots = map.get(1).stream().map(r -> {
            return new CategoryPureVO(r);
        }).collect(Collectors.toList());

        this.subs = map.get(2).stream().map(r -> {
            return new CategoryPureVO(r);
        }).collect(Collectors.toList());
    }
}
