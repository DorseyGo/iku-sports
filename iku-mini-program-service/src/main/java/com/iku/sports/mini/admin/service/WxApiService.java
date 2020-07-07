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
import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.request.GetPrepayIdRequest;
import com.iku.sports.mini.admin.request.QueryParams;
import com.iku.sports.mini.admin.response.PrepaymentResponse;
import com.iku.sports.mini.admin.utils.Obj2Map;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpPrepayIdResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
            throw new ApiServiceException(IkuSportsError.LOGIN_ERR);
        } catch (IOException e) {
            log.error("==> Failed to parse the json: {}", json, e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    /**
     * Uniform new an order.
     *
     * @param openId the open ID.
     * @param orderNo the order number.
     * @param fee the fee in YUAN.
     * @param body the production description.
     * @param tradeType the trade type, availables see {@link com.iku.sports.mini.admin.model.Constants.TradeType}
     * @param issuer the IP address of the issuer, from which the request is sent.
     * @param notifyUrl the notify url for callback.
     * @return the response.
     * @throws ApiServiceException if prepay ID not generated accordingly.
     */
    public PrepaymentResponse getPrepayId(final String openId, final String orderNo, final double fee,
            final String body, final Constants.TradeType tradeType, final String issuer, final String notifyUrl) throws ApiServiceException {
        final GetPrepayIdRequest request = GetPrepayIdRequest.builder()
                .openId(openId)
                .orderNo(orderNo)
                .totalFee(fee)
                .productDescription(body)
                .tradeType(tradeType.name())
                .terminalIpAddr(issuer)
                .notifyUrlAddr(notifyUrl)
                .build();

        WxMpPrepayIdResult result = wxMpService.getPrepayId(Obj2Map.fromObj(request));
        String prepayId = null;
        if (Constants.SUCCESS.equals(result.getReturn_code()) && Constants.SUCCESS.equals(
                result.getResult_code())) {
            prepayId = result.getPrepay_id();
        }

        if (prepayId == null) {
            log.error("==> No prepay ID generate when issue request to WeChat");
            throw new ApiServiceException(IkuSportsError.UNIFORM_ORDER_ERR);
        }

        return PrepaymentResponse.builder()
                .appId(result.getAppid())
                .nonce(result.getNonce_str())
                .sign(result.getSign())
                .prepayId(prepayId)
                .build();
    }
}
