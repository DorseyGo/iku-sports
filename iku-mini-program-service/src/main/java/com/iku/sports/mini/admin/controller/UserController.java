/**
 * File: UserController
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 13:44
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.request.LoginRequest;
import com.iku.sports.mini.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {this.userService = userService;}

    /**
     * Login, and fetch openID and session key from WeChat server.
     *
     * @param loginRequest the code, according to which the open ID and session key can be fetched.
     * @return the token which implies the open ID and session key.
     */
    @WebLog
    @PostMapping("/user/login")
    public Response<String> login(@RequestBody LoginRequest loginRequest) throws ApiServiceException {
        final String token = userService.doLoginAndReturnToken(loginRequest.getCode());
        return Response.ok(token);
    }
}
