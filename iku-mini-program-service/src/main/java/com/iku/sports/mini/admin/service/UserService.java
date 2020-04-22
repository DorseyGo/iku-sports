/**
 * File: UserService
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:34
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import javax.validation.constraints.NotNull;

public interface UserService {
    String doLoginAndReturnToken(final String code) throws ApiServiceException;

    String getOpenIdByToken(final String token) throws ApiServiceException;

    User getUserByToken(@NotNull String token);
}
