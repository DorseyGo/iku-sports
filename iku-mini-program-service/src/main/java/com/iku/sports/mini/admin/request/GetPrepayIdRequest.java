/*
 * File: Payment
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.iku.sports.mini.admin.annotation.Map;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
public class GetPrepayIdRequest {

    @Map(key = "body")
    @Length(max = 128)
    private String productDescription;

    @Map(key = "out_trade_no")
    @Length(max = 32)
    private String orderNo;

    @Map(key = "total_fee")
    private int totalFee;

    @Map(key = "spbill_create_ip")
    @Length(max = 64)
    private String terminalIpAddr;

    @URL
    @Length(max = 256)
    @Map(key = "notify_url")
    private String notifyUrlAddr;
    private String tradeType = TradeType.JSAPI.name();
    private String openId;

    /**
     * The trade type
     */
    public enum TradeType {
        JSAPI, NATIVE, APP;
    }
}
