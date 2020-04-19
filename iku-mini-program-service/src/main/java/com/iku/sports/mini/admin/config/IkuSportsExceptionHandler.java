/**
 * File: IkuSportsExceptionHandler
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 15:14
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.config;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class IkuSportsExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ApiServiceException.class})
    public Response<String> handleApiServiceException(final ApiServiceException ex) {
        return new Response<String>().status(ex.getCode()).errorPhase(ex.getMessage());
    }
}
