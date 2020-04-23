/**
 * File: UserServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/4/18 14:37
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.User;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.repository.UserRepository;
import com.iku.sports.mini.admin.request.JSCode2SessionParams;
import com.iku.sports.mini.admin.request.QueryParams;
import com.iku.sports.mini.admin.response.GetOpenIdAndSessionKeyResponse;
import com.iku.sports.mini.admin.service.UserService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    static final String URL_JS_CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";
    private final WxMpService wxMpService;
    private final IkuSportsConfig config;
    private final ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final LoadingCache<String, String> cache;

    @Autowired
    public UserServiceImpl(@Qualifier("wxMpService") WxMpService wxMpService,
            IkuSportsConfig config, @Qualifier("userRepository") UserRepository userRepository) {
        this.wxMpService = wxMpService;
        this.config = config;
        this.userRepository = userRepository;
        cache = CacheBuilder.newBuilder().expireAfterWrite(config.getExpiryInDays(), TimeUnit.DAYS)
                .expireAfterAccess(config.getExpiryInDays(), TimeUnit.DAYS).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return createValue(key);
                    }
                });
    }

    private String createValue(final String token) {
        User user = userRepository.findUserByToken(token);
        return (user == null) ? null : user.getOpenId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String doLoginAndReturnToken(String code) throws ApiServiceException, DataAccessException {
        if (Strings.isNullOrEmpty(code)) {
            log.error("Code required");
            throw new ApiServiceException(IkuSportsError.SYS_PARAMS_MISSED);
        }

        final GetOpenIdAndSessionKeyResponse resp = getOpenIdAndSessionKey(code);
        final String userId = UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
        final String token = Utils.genUniqueStr();

        userRepository.save(User.builder()
                .id(userId)
                .openId(resp.getOpenId())
                .token(token)
                .build());

        return token;
    }

    @Override
    public String getOpenIdByToken(String token) throws ApiServiceException {
        try {
            return cache.get(token);
        } catch (ExecutionException e) {
            log.error("Failed to fetch open id by token {}", token, e);
            throw new ApiServiceException(IkuSportsError.OPEN_ID_NOT_FOUND_ERROR);
        }
    }

    @Override
    public User getUserByToken(@NotNull String token) {
        return userRepository.findUserByToken(token);
    }

    private GetOpenIdAndSessionKeyResponse getOpenIdAndSessionKey(String code) throws ApiServiceException {
        QueryParams queryParams = JSCode2SessionParams.builder()
                .appId(config.getAppId())
                .jsCode(code)
                .secret(config.getSecret())
                .build();

        GetOpenIdAndSessionKeyResponse resp = get(URL_JS_CODE_2_SESSION, queryParams,
                GetOpenIdAndSessionKeyResponse.class);

        log.debug("OpenId And session key: {}", resp);
        return resp;
    }

    /**
     * Issue a request to get the open ID.
     *
     * @throws ApiServiceException
     */
    private <T> T get(final String uri, final QueryParams queryParams, final Class<T> resp) throws ApiServiceException {
        try {
            final String result = wxMpService.execute(new SimpleGetRequestExecutor(), uri,
                    Utils.genQueryParams(queryParams));
            return mapper.readValue(result.getBytes(), resp);
        } catch (WxErrorException | IOException e) {
            log.error("Failed to issue request to {}", URL_JS_CODE_2_SESSION, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERROR);
        }
    }
}
