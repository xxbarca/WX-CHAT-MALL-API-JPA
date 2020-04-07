package com.li.missyou.util;

import com.li.missyou.bo.PageCounter;

import java.util.Map;

public class CommonUtil {

    public static PageCounter convertToPageParameter(Integer start, Integer count) {
        int pageNum = start / count;
        PageCounter pageCounter = PageCounter.builder()
                                            .page(pageNum)
                                            .count(count)
                                            .build();
        return pageCounter;
    }
}
