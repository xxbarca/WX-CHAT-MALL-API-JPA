package com.li.missyou.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

// 让注解里面的注释加入到文档里面
@Documented
// 注解要保留到的阶段
@Retention(RetentionPolicy.RUNTIME)
// 指定注解用到的目标
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    int min() default 4;

    int max() default 6;

    String message() default "passwords are not equal";

    // 两个模版方法
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // 关联类

}
