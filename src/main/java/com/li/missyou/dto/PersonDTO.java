package com.li.missyou.dto;

import com.li.missyou.validators.PasswordEqual;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

//@Getter
//@Setter
//@NoArgsConstructor
//@RequiredArgsConstructor
//@AllArgsConstructor
@Builder
@Setter
@Getter
@PasswordEqual(min = 1, message = "两次密码不相同")
public class PersonDTO {

    @Length(min = 2, max = 10, message = "xxxxx")
    private String name;

    private Integer age;

    private String password1;
    private String password2;

}
