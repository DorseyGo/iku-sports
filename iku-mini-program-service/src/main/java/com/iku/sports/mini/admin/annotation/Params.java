/**
 * File: Params
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:49
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Params {

    /**
     * Returns the key of the parameters.
     *
     *
     * @return the key of the parameter.
     */
    String key() default "";
}
