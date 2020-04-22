/**
 * File: UserController
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 13:44
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.LoginRequest;
import com.iku.sports.mini.admin.response.LoginResponse;
import com.iku.sports.mini.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {this.userService = userService;}

    @ResponseBody
    @PostMapping("/api/user/login")
    public Response<LoginResponse> login(@RequestBody final LoginRequest request) throws ApiServiceException {
        final String token = userService.doLoginAndReturnToken(request.getCode());
        if (Strings.isNullOrEmpty(token)) {
            throw new ApiServiceException(IkuSportsError.LOGIN_ERROR);
        }

        return new Response<LoginResponse>().status(Response.SUCCESS)
                .data(LoginResponse.builder()
                              .token(token)
                              .build());
    }
}
