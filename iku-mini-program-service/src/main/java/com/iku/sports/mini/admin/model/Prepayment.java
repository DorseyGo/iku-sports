/*
 * File: Prepayment
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Prepayment {
    private String appId;
    private String timeStamp;
    private String nonce;
    private String pckage;
    @Builder.Default
    private String signType = Constants.SignType.MD5.getType();

    // -----------------------
    // computed, when prepayment is prepared.
    // -----------------------
    private String sign;

    @Tolerate
    public Prepayment() {}

}
