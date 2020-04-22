/*
 * File: PrepaymentResponse
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.response;

import com.iku.sports.mini.admin.annotation.Map;
import com.iku.sports.mini.admin.model.Prepayment;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
public class PrepaymentResponse extends Prepayment {
    private String sign;

    public PrepaymentResponse() {}
}
