/**
 * File: IkuSportsError
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:40
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.exception;

import java.util.Locale;

/**
 * Enumeration, which enumerates all available errors.
 *
 */
public enum IkuSportsError {
    INTERNAL_ERR(2000, "Server Internal Error"),
    SYSTEM_ERR(2001, "Connection error"),

    // ------
    // Parameters error
    // ------
    PARAM_VALIDATION_FAIL(-1, "Parameters required error");

    private final int errorCode;
    private final String errorMessage;

    /**
     * Default constructor of {@link IkuSportsError}, with error code and message specified.
     *
     * @param errorCode    the error code.
     * @param errorMessage the error message.
     */
    IkuSportsError(final int errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "{errorCode: %d, errorMessage: %s}",
                errorCode, errorMessage);
    }
}
