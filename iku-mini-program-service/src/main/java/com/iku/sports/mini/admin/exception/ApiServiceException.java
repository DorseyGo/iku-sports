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

    /**
     * Constructor of {@link ApiServiceException}, with error specified.
     *
     * @param error the error with code and message specified.
     */
    public ApiServiceException(final IkuSportsError error) {
        this(error.getErrorCode(), error.getErrorMessage());
    }

    private ApiServiceException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
