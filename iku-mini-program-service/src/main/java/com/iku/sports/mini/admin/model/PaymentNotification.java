/*
 * File: PaymentNotification
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class PaymentNotification {

    @XmlElement(name = "appid")
    private String appId;

    @XmlElement(name = "nonce_str")
    private String nonceStr;

    @XmlElement(name = "sign")
    private String sign;

    @XmlElement(name = "return_code")
    private String returnCode;

    @XmlElement(name = "result_code")
    private String resultCode;

    @XmlElement(name = "err_code")
    private String errCode;

    @XmlElement(name = "err_code_des")
    private String errCodeDesc;

    @XmlElement(name = "openid")
    private String openId;

    @XmlElement(name = "is_subscribe")
    private String isSubscribe;

    @XmlElement(name = "trade_type")
    private String tradeType;

    @XmlElement(name = "bank_type")
    private String bankType;

    @XmlElement(name = "total_fee")
    private int totalFee;

    @XmlElement(name = "settlement_total_fee")
    private int settlementTotalFee;

    @XmlElement(name = "cash_fee")
    private int cashFee;

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "out_trade_no")
    private String orderNo;

    @XmlElement(name = "time_end")
    private String endTime;

    @Tolerate
    public PaymentNotification() {}
}
