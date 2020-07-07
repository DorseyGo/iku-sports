/*
 * File: Prepayment
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.annotation.Key;
import com.iku.sports.mini.admin.request.WxRequest;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * The prepayment, which generated after invoking prepay in WeChat.
 * <p>
 * After this generated, client can issue payment request with arguments derived from this class.
 * </p>
 */
@Data
@Builder
public class Prepayment implements WxRequest {

    @Key("appId")
    private String appId;

    @Key("timeStamp")
    private String timeStamp;

    @Key("nonceStr")
    private String nonce;

    @Key("package")
    private String pckage;

    @Key("signType")
    @Builder.Default
    private String signType = Constants.SignType.MD5.getType();

    // -----------------------
    // computed, when prepayment is prepared.
    // this is generated based on above five fields.
    // -----------------------
    private String sign;

    @Tolerate
    public Prepayment() {}

}
