package com.li.missyou.service;

import com.li.missyou.model.Tag;
import com.li.missyou.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    public List<Tag> findByType(Integer type) {
        return tagRepository.findByType(type);
    }
}
