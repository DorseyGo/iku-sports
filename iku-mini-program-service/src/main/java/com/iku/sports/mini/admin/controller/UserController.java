/**
 * File: UserController
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 13:44
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.LoginRequest;
import com.iku.sports.mini.admin.response.LoginResponse;
import com.iku.sports.mini.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {this.userService = userService;}

    @WebLog
    @PostMapping("/api/user/login")
    public Response<LoginResponse> login(@RequestBody final LoginRequest request) throws ApiServiceException {
        /* returns the user ID as token to the front-end */
        final String token = userService.doLoginAndReturnToken(request.getCode());
        if (Strings.isNullOrEmpty(token)) {
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        return Response.ok(LoginResponse.builder()
                        .token(token)
                        .build());
    }

    @WebLog
    @GetMapping("/api/users/{userId}")
    public Response<User> getUserById(@PathVariable("userId") final String userId) throws ApiServiceException {
        User user = userService.getUserById(userId);
        return Response.ok(user);
    }
}
