/**
 * File: IkuSportsExceptionHandler
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 15:14
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.config;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
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
        return Response.fail(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public Response<String> handleConstraintValidationException(final ConstraintViolationException ex) {
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            log.error("==> Error detected when validate parameter: {}, with error: {}",
                    violation.getPropertyPath().toString(), violation.getMessage(), ex);
            return Response.fail(IkuSportsError.PARAM_VALIDATION_FAIL);
        }

        return Response.fail("Parameter validation error");
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Response<String> handleUnknownException(final Exception ex) {
        log.error("==> Error detected when request", ex);

        return Response.fail(IkuSportsError.SYSTEM_ERR);
    }
}
