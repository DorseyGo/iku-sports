/**
 * File: ApiServiceException
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:35
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.exception;

/**
 * The exception which is thrown when API invocation failed.
 */
public class ApiServiceException extends Exception {
    private final int code;

    public ApiServiceException(final IkuSportsError error) {
        this(error.getCode(), error.getMessage());
    }

    public ApiServiceException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
