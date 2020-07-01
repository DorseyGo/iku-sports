package com.iku.sports.mini.admin.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target(value = { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    /**
     * 是否要记录响应结果
     * @return
     */
    boolean response() default true;
}
