/*
 * File: Prepayment
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.annotation.Map;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Prepayment {
    @Map(key = "appId")
    protected String appId;

    @Map(key = "timeStamp")
    protected String timeStamp;

    @Map(key = "nonceStr")
    protected String nonceStr;

    @Map(key = "package")
    protected String pckage;

    @Map(key = "signType")
    @Builder.Default
    protected String signType = SignType.MD5.getType();

    @Tolerate
    public Prepayment() {}


    public enum SignType {
        MD5("MD5"), HMAC_SHA256("HMAC-SHA256");
        private final String type;
        SignType(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
