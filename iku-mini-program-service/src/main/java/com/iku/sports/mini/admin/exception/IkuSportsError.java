/**
 * File: IkuSportsError
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:40
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.exception;

public enum IkuSportsError {

    SYS_PARAMS_MISSED(1001, "请求参数不够"), INTERNAL_ERROR(500, "服务器内部错误"), LOGIN_ERROR(1000, "登录失败");

    private final int code;
    private final String message;

    IkuSportsError(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
