/**
 * File: UserServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:37
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.google.common.base.Strings;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.UserRepository;
import com.iku.sports.mini.admin.request.JSCode2SessionParams;
import com.iku.sports.mini.admin.response.GetOpenIdAndSessionKeyResponse;
import com.iku.sports.mini.admin.service.UserService;
import com.iku.sports.mini.admin.service.WxApiService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    private final WxApiService wxApiService;
    private final IkuSportsConfig config;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(@Qualifier("wxApiService") WxApiService wxApiService, IkuSportsConfig config,
            @Qualifier("userRepository") UserRepository userRepository) {
        this.wxApiService = wxApiService;
        this.config = config;
        this.userRepository = userRepository;
    }

    @Override
    public String doLoginAndReturnToken(final String code) throws ApiServiceException {
        if (Strings.isNullOrEmpty(code)) {
            log.error("==> Required code, but it's null or empty");
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        final GetOpenIdAndSessionKeyResponse resp = wxApiService.get(Constants.URL_JS_CODE_2_SESSION,
                JSCode2SessionParams.builder().appId(config.getAppId())
                        .secret(config.getSecret()).jsCode(code).build(), GetOpenIdAndSessionKeyResponse.class);

        if (resp.getErrorCode() != 0) {
            log.error("==> Failed to get open id and session key, due to error: {}", resp.getErrorMessage());
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        return saveOpenIdAndSessionKey(resp.getOpenId(), resp.getSessionKey());
    }

    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public String saveOpenIdAndSessionKey(String openId, String sessionKey) throws DataAccessException {
        final String id = Utils.genUniqueStr();
        final User user = User.builder().id(id).openId(openId).sessionKey(sessionKey).build();
        userRepository.save(user);

        return id;
    }
}
