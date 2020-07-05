/*
 * File: Payment
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.iku.sports.mini.admin.annotation.Key;
import com.iku.sports.mini.admin.model.Constants;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
public class GetPrepayIdRequest implements WxRequest {

    @Key( "body")
    @Length(max = 128)
    private String productDescription;

    @Key("out_trade_no")
    @Length(max = 32)
    private String orderNo;

    @Key("total_fee")
    private double totalFee;

    @Key("spbill_create_ip")
    @Length(max = 64)
    private String terminalIpAddr;

    @URL
    @Length(max = 256)
    @Key("notify_url")
    private String notifyUrlAddr;

    @Key("trade_type")
    private String tradeType = Constants.TradeType.JSAPI.name();

    @Key("openid")
    private String openId;

    @Tolerate
    public GetPrepayIdRequest() {
        // empty for initialization.
    }
}
