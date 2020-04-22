/*
 * File: Result
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.util.Locale;

@Data
@Builder
public class Result {
    private final String returnCode;
    private final String returnMsg;

    public String toXml() {
        return String.format(Locale.ROOT,
                "<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]></return_msg></xml>",
                getReturnCode(), getReturnMsg());
    }
}
