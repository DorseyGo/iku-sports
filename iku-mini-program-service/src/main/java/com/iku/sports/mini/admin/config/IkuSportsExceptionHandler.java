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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
    @ExceptionHandler({ConstraintViolationException.class})
    public Response<String> handleConstraintValidationException(final ConstraintViolationException ex) {
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            log.error("error occurs when validate parameter, field:[{}], errorMsg:[{}]",
                    violation.getPropertyPath().toString(), violation.getMessage(), ex);
            return Response.fail(ex.getMessage());
        }

        return Response.fail("请求参数错误");
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Response<String> handleUnknownException(final Exception ex) {
        log.error("error occurs when request", ex);

        return Response.fail("网络异常，请稍后重试");
    }
}
