/*
 * File: WxApiService
 * Author: DorSey Q F TANG
 * Created: 2020/7/1
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.request.QueryParams;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service("wxApiService")
public class WxApiService {
    private final WxMpService wxMpService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public WxApiService(@Qualifier("wxMpService") final WxMpService wxMpService) {this.wxMpService = wxMpService;}

    /**
     * Issued a <tt>GET</tt> request to WeChat server, and wrap the result into an object.
     *
     * @param url
     * @param params
     * @param <T>
     * @return
     * @throws ApiServiceException
     */
    public <T> T get(final String url, final QueryParams params, Class<T> resp) throws ApiServiceException {
        String json = null;
        try {
            json = wxMpService.execute(new SimpleGetRequestExecutor(), url, Utils.genQueryParams(params));
            return objectMapper.readValue(json.getBytes(StandardCharsets.UTF_8), resp);
        } catch (WxErrorException ex) {
            log.error("==> Failed to issue request to wechat server", ex);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        } catch (IOException e) {
            log.error("==> Failed to parse the json: {}", json, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }
}
