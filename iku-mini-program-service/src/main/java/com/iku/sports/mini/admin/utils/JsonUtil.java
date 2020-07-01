package com.iku.sports.mini.admin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.chanjar.weixin.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * json 工具
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 设置一些通用的属性
     */
    private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER = ThreadLocal.withInitial(() -> {
        ObjectMapper objectMapper = new ObjectMapper();
        // 如果存在未知属性，则忽略不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                    .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                    // 允许key有单引号
                    .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                    // timestamp
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    // date format
                    .setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        return objectMapper;
    });

    public static String toJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "") : "";
    }

    /**
     * 对象转 json 字符串
     * @param obj
     * @param defaultSupplier 对象为空时，默认字符串
     * @return
     */
    public static String toJSONString(Object obj, Supplier<String> defaultSupplier) {
        try {
            return obj != null ? OBJECT_MAPPER.get().writeValueAsString(obj) : defaultSupplier.get();
        } catch (Throwable e) {
            LOGGER.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), e);
        }

        return defaultSupplier.get();
    }

    public static <T> T toJavaObject(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? toJavaObject(value, tClass, () -> null) : null;
    }

    public static <T> T toJavaObject(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObject(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> T toJavaObject(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return OBJECT_MAPPER.get().readValue(value, tClass);
        } catch (Throwable e) {
            LOGGER.error(String.format("toJavaObject exception: %s %s", value, tClass), e);
        }

        return defaultSupplier.get();
    }

    public static <T> List<T> toJavaObjectList(String value, Class<T> tClass) {
        return StringUtils.isNotBlank(value) ? toJavaObjectList(value, tClass, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(Object obj, Class<T> tClass) {
        return obj != null ? toJavaObjectList(toJSONString(obj), tClass, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            JavaType javaType = OBJECT_MAPPER.get().getTypeFactory().constructParametricType(List.class, tClass);
            return OBJECT_MAPPER.get().readValue(value, javaType);
        } catch (Throwable e) {
            LOGGER.error(String.format("toJavaObjectList exception %s %s", value, tClass), e);
        }

        return defaultSupplier.get();
    }

    public static Map<String, Object> toMap(String value) {
        return StringUtils.isNotBlank(value) ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object value) {
        return value != null ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object value, Supplier<Map<String, Object>> defaultSupplier) {
        if (value == null) {
            return defaultSupplier.get();
        }
        try {
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            }
        } catch (Exception e) {
            LOGGER.error("fail to convert" + toJSONString(value), e);
        }
        return toMap(toJSONString(value), defaultSupplier);
    }

    public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
        if (StringUtils.isBlank(value)) {
            return defaultSupplier.get();
        }
        try {
            return toJavaObject(value, HashMap.class);
        } catch (Exception e) {
            LOGGER.error(String.format("toMap exception\n%s", value), e);
        }

        return defaultSupplier.get();
    }
}
