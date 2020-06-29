/**
 * File: Response
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.List;

/**
 * The response entity, which represents the info responded.
 *
 * @param <T>
 */
public class Response<T> implements Serializable {
    private int statusCode;
    private String errorPhase;
    private T data;

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    /**
     * Sole constructor of {@link Response}, with status code, error phase and data specified.
     *
     * @param statusCode the status code.
     * @param errorPhase the error phase.
     * @param data the data.
     */
    private Response(final int statusCode, final String errorPhase, final T data) {
        this.statusCode = statusCode;
        this.errorPhase = errorPhase;
        this.data = data;
    }

    public static <T> Response<T> ok(final T data) {
        return new Response<>(SUCCESS, null, data);
    }

    public static <T> Response<T> ok() {
        return new Response<>(SUCCESS, null, null);
    }

    public static <T> Response<T> fail(final int statusCode, final String errorPhase) {
        return new Response<>(statusCode, errorPhase, null);
    }
}
