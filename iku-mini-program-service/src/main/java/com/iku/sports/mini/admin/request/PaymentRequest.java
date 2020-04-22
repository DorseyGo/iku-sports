/*
 * File: PaymentRequest
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class PaymentRequest {

    @JsonProperty("token")
    private final String token;

    @JsonProperty("orderNo")
    private final String orderNo;
}
