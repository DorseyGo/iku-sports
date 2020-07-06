/**
 * File: PrepaymentResponse
 * Author: DorSey Q F TANG
 * Created: 2020/7/5 11:22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.response;

import com.iku.sports.mini.admin.model.Constants;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class PrepaymentResponse {
    private String appId;
    private String nonce;
    private String sign;
    @Builder.Default
    private String tradeType = Constants.TradeType.JSAPI.name();
    private String prepayId;

    @Tolerate
    public PrepaymentResponse() {
        // empty for initialization.
    }
}
