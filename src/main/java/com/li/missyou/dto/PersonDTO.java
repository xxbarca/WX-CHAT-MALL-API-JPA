package com.li.missyou.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

//@Getter
//@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor
//@AllArgsConstructor
@Builder
@Setter
@Getter
public class PersonDTO {

    @Length(min = 2, max = 10, message = "xxxxx")
    private String name;

    private Integer age;

    private String password1;
    private String password2;

}
