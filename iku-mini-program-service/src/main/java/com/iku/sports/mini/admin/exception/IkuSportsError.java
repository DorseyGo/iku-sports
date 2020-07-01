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
 * 错误代码
 * 错误代码[1000, 2000)，统一返回前端错误信息 网络异常，请稍后重试；
 * 错误代码[2000, 3000)，原样返回错误代码对应的异常信息
 *
 */
public enum IkuSportsError {
    INTERNAL_ERR(2000, "系统内部错误");

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
