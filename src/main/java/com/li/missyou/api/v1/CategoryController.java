package com.li.missyou.api.v1;

import com.li.missyou.exception.http.NotFoundException;
import com.li.missyou.model.Category;
import com.li.missyou.model.GridCategory;
import com.li.missyou.service.CategoryService;
import com.li.missyou.service.GridCategoryService;
import com.li.missyou.vo.CategoryAllVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    public CategoryAllVo getAll() {
        Map<Integer, List<Category>> categories = categoryService.getAll();
        log.info("data = {}", categories);
        return new CategoryAllVo(categories);
    }

    @GetMapping("/grid/all")
    private List<GridCategory> getGridCategory() {
        List<GridCategory> gridCategories = gridCategoryService.getGridCategoryList();
        if (gridCategories.isEmpty()) {
            throw new NotFoundException(30009);
        }
        return gridCategories;
    }
}
