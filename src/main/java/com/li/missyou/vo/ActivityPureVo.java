package com.li.missyou.vo;

import com.li.missyou.model.Activity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
@Setter
public class ActivityPureVo {

    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private Boolean online;
    private String entranceImg;
    private String remark;

    public ActivityPureVo(Activity activity) {
        BeanUtils.copyProperties(activity, this);
    }

}
