/**
 * File: Response
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * The response entity, which represents the info responded.
 *
 * @param <T>
 */
@Data
public class Response<T> implements Serializable {
    private int statusCode;
    private String errorPhase;
    private T data;

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
        return new Response<>(Constants.OK_REQ, null, data);
    }

    public static <T> Response<T> ok() {
        return new Response<>(Constants.OK_REQ, null, null);
    }

    public static <T> Response<T> fail(final int statusCode, final String errorPhase) {
        return new Response<>(statusCode, errorPhase, null);
    }

    public static <T> Response<T> fail(final String msg) {
        return new Response<>(Constants.UNKNOWN_FAIL, msg, null);
    }
}
