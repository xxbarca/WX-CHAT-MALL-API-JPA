package com.li.missyou.service;

import com.li.missyou.model.Banner;
import org.springframework.stereotype.Service;

public interface BannerService {
    Banner getByName(String name);
}
