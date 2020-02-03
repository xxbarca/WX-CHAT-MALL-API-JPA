package com.li.missyou.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SchoolDTO {

    @Length(min = 2, max = 10, message = "schoolName")
    private String schoolName;
}
