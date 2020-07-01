/**
 * File: IkuSportsExceptionHandler
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 15:14
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.config;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class IkuSportsExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ApiServiceException.class})
    public Response<String> handleApiServiceException(final ApiServiceException ex) {
        log.error(ex.getMessage(), ex);

        int errorCode = ex.getCode();
        if (errorCode < 2000) {
            return Response.fail("网络异常，请稍后重试");
        }

        return Response.fail(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Response<String> handleUnknownException(final Exception ex) {
        log.error("error occurs when request", ex);

        return Response.fail("网络异常，请稍后重试");
    }
}
