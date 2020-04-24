/**
 * File: IkuSportsError
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:40
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.exception;

public enum IkuSportsError {

    SYS_PARAMS_MISSED(1001, "请求参数错误"), INTERNAL_ERROR(500, "服务器内部错误"), LOGIN_ERROR(1000,
            "登录失败"), OPEN_ID_NOT_FOUND_ERROR(
            10001, "Open ID找不到"), ORDER_NOT_FOUND_ERROR(10002, "找不到相关订单"), ORDER_ERROR(10003,
            "订单信息错误"), REQ_WX_API_ERROR(
            1002, "请求微信API失败"), XML_PARSE_ERROR(10004, "XML解析失败"), REQ_RESOURCE_NOT_FOUND_ERR(10005, "请求资源找不到");

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
