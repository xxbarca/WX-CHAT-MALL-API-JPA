package com.li.missyou.service;

import com.li.missyou.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    public Map<Integer, List<Category>> getAll();
}
