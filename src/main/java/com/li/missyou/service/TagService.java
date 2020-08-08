package com.li.missyou.service;

import com.li.missyou.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findByType(Integer type);
}
