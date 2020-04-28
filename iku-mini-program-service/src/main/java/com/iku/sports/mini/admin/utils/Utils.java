/**
 * File: Utils
 * Author: DorSey Q F TANG
 * Created: 2020/4/5 10:51
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.annotation.Params;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.request.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The utility class.
 */
@Slf4j
public class Utils {

    /**
     * Join the <tt>prefix</tt> with <tt>suffix</tt> via delimiter.
     * <p>
     * For example:
     * <ul>
     * <li>prefix: http://localhost:9090/</li>
     * <li>suffix: /api/services</li>
     * <li>delimiter: /</li>
     * </ul>
     * then after called, <tt>http://localhost:9090/api/services</tt> will be returned.
     * </p>
     *
     * @param prefix the prefix
     * @param suffix the suffix
     * @param delim  the delimiter
     * @return the joiner, with prefix and suffix joined with delimiter.
     */
    public static String join(final String prefix, final String suffix, final String delim) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(prefix), "Prefix missed");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(suffix), "suffix missed");

        return prefix.replaceAll(delim + "$", "")
                + delim + suffix.replaceAll("^" + delim, "");
    }

    public static <T extends QueryParams> String genQueryParams(final T queryParams) {
        final List<String> params = Lists.newArrayList();

        ReflectionUtils.doWithFields(queryParams.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            String param = String.format(Locale.ROOT, "%s=%s",
                                         field.getAnnotation(Params.class)
                                                 .key(),
                                         field.get(queryParams));

            params.add(param);
        }, field -> field.isAnnotationPresent(Params.class));

        return Joiner.on("&").join(params).toString();
    }

    public static String genUniqueStr() {
        String origin = genUUID(Constants.LEN_32);
        origin += String.valueOf(System.currentTimeMillis());

        return md5(origin);
    }

    public static String md5(final String source) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes(StandardCharsets.UTF_8));

            return convertToHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertToHex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < bytes.length; index++) {
            builder.append(Integer.toHexString(0xff & bytes[index]));
        }

        return builder.toString();
    }

    public static <T> Map<String, String> toMap(final T obj) {
        if (obj == null) {
            return Maps.newHashMapWithExpectedSize(0);
        }

        final Map<String, String> params = Maps.newHashMapWithExpectedSize(32);
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            com.iku.sports.mini.admin.annotation.Map map = field.getAnnotation(com.iku.sports.mini.admin.annotation.Map.class);
            params.put(map.key(), (String) field.get(obj));
        }, field -> field.isAnnotationPresent(com.iku.sports.mini.admin.annotation.Map.class));

        return params;
    }

    public static <T> SortedMap<String, String> toSortedMap(final T obj) {
        Map<String, String> hashMap = toMap(obj);

        SortedMap<String, String> sortedMap = Maps.newTreeMap();
        sortedMap.putAll(hashMap);

        return sortedMap;
    }

    public static String getTimestamp() {
        return String.valueOf((System.currentTimeMillis() / 1000));
    }

    public static String genUUID(final int len) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if (uuid.length() < len) {
            return Strings.padEnd(uuid, len, '0');
        }

        return uuid.substring(0, len);
    }

    public static String sign(final SortedMap<String, String> params, final String key) {
        final StringBuilder builder = new StringBuilder();
        params.forEach((k, value) -> {
            if (!Strings.isNullOrEmpty(value) && !k.equalsIgnoreCase(key)) {
                builder.append(String.format(Locale.ROOT, "%s=%s&", k, value));
            }
        });

        builder.append(String.format(Locale.ROOT, "key=%s", key));
        return md5(builder.toString()).toUpperCase();
    }

    public static byte[] read(final InputStream is) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            baos.flush();
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                baos.close();
            }

            if (is != null) {
                is.close();
            }
        }
    }

    public static int paginateOffset(int curPage, int pageSize) {
        return (curPage - 1) * pageSize;
    }
}
