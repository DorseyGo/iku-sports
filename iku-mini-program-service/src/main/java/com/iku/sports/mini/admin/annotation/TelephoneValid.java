package com.iku.sports.mini.admin.annotation;

import com.iku.sports.mini.admin.valid.PhoneNumberValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface TelephoneValid {
    String message() default "请输入正确的手机号";
    Class[] groups() default {};
    Class[] payload() default {};
}
