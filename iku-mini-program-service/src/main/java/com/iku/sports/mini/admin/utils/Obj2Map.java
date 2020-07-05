/**
 * File: Obj2Map
 * Author: DorSey Q F TANG
 * Created: 2020/7/5 11:43
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.utils;

import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.annotation.Key;
import com.iku.sports.mini.admin.request.WxRequest;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * The utility class, for converting object to map and vice versa.
 *
 */
public class Obj2Map {

    /**
     * Convert the given object to a mapping. Returns an empty map if the given object is null.
     *
     * @param obj the object.
     * @param <T>
     * @return the mapping.
     */
    public static <T extends WxRequest> Map<String, String> fromObj(final T obj) {
        if (obj == null) {
            return Maps.newHashMapWithExpectedSize(0);
        }

        final Map<String, String> result = Maps.newHashMap();
        final Class<? extends WxRequest> clazz = obj.getClass();
        ReflectionUtils.doWithFields(clazz, field -> {
            final Key key = field.getAnnotation(Key.class);
            if (key.value()
                    .trim()
                    .isEmpty()) {
                throw new IllegalArgumentException(
                        "Annotation Key annotated on field: " + field.getName() + " is illegal");
            }

            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, obj);
            result.put(key.value(), String.valueOf(value));
        }, field -> field.isAnnotationPresent(Key.class));

        return result;
    }
}
