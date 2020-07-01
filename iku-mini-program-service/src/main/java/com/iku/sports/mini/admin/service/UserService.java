/**
 * File: UserService
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:34
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.exception.ApiServiceException;

/**
 * The service, which declares the service provided for <tt>user</tt>-kind request.
 */
public interface UserService {

    /**
     * Login to WeChat server, and store the open ID and session key into the underlying database.
     *
     * @param code the code.
     * @return the token, which indicates the open ID and session key.
     * @throws ApiServiceException if any error detected during the process.
     */
    String doLoginAndReturnToken(final String code) throws ApiServiceException;
}
