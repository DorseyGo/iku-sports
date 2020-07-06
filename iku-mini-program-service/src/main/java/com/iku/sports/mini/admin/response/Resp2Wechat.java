/*
 * File: WxResponse
 * Author: DorSey Q F TANG
 * Created: 2020/7/6
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resp2Wechat {

    /**
     * The return code, available are: SUCCESS/FAIL
     */
    private final String returnCode;

    /**
     * The return message.
     */
    private final String returnMessage;

}
