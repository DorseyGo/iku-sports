/**
 * File: UserController
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 13:44
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.google.common.base.Strings;
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

    /**
     * Login, and fetch openID and session key from WeChat server.
     *
     * @param code the code, according to which the open ID and session key can be fetched.
     * @return the token which implies the open ID and session key.
     */
    @RequestMapping("/user/login")
    public Response<String> login(@RequestParam("code") final String code) throws ApiServiceException {
        final String token = userService.doLoginAndReturnToken(code);
        return Response.ok(token);
    }
}
