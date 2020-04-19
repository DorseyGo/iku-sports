/**
 * File: UserService
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:34
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.exception.ApiServiceException;

public interface UserService {
    String doLoginAndReturnUserId(final String code) throws ApiServiceException;
}
