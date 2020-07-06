/*
 * File: XMLUtils
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.utils;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.PaymentNotification;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
public class XMLUtils {
    public static <T extends PaymentNotification> T convertXmlStrToObj(final Class<T> clazz, final String xml) throws ApiServiceException {
        T obj = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xml);

            obj = (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            log.error("Failed to parse the xml: {} to class: {}", xml, clazz.getName());
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }

        return obj;
    }

    public static void main(String[] args) throws ApiServiceException {
        final String xml = "<xml>\n" +
                           "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                           "  <attach><![CDATA[支付测试]]></attach>\n" +
                           "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                           "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                           "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                           "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                           "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                           "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                           "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                           "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                           "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                           "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                           "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                           "  <total_fee>1</total_fee>\n" +
                           "  <coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                           "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                           "  <coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                           "  <coupon_id><![CDATA[10000]]></coupon_id>\n" +
                           "  <coupon_fee><![CDATA[100]]></coupon_fee>\n" +
                           "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                           "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                           "</xml>";
        PaymentNotification notification = convertXmlStrToObj(PaymentNotification.class, xml);
        System.out.println(notification.getTransactionId());
    }
}
