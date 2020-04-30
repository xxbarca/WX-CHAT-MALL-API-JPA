package com.li.missyou.service;

import com.li.missyou.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeService {

    List<Theme> findByNames(List<String> names);

    Optional<Theme> findByName(String themeName);
}
