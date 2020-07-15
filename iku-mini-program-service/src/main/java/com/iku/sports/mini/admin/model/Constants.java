/**
 * File: Constants
 * Author: DorSey Q F TANG
 * Created: 2020/4/5 11:02
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.model;

import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The constants which enumerates all available constants used in entire project.
 */
public interface Constants {
    String FORWARD_SLASH = "/";
    int OK_REQ = 0;
    int FAIL_REQ = -1;

    BigDecimal ONE_HUNDRED = new BigDecimal(100);

    String DELIM_COMMA = ",";
    String DELIM_DOT = ".";

    /* configurations for WeChat */
    String URL_JS_CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";
    /* retain how many numbers */
    int DIVIDE_SCALE = 2;
    String SUCCESS = "SUCCESS";
    int LEN_32 = 32;
    String PREFIX_PREPAY_ID = "prepay_id=";
    String OK = "OK";
    String FAIL = "FAIL";

    int DEFAULT_PAGE_SIZE = 5;
    String RESP_2_WECHAT = "<xml>\n" +
                           "  <return_code><![CDATA[%s]]></return_code>\n" +
                           "  <return_msg><![CDATA[%s]]></return_msg>\n" +
                           "</xml>";

    String DATE_PATTERN_WECHAT = "yyyyMMddHHmmss";
    ThreadLocal<DateFormat> DATE_FORMATTER_WECHAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DATE_PATTERN_WECHAT);
        }
    };

    @Slf4j
    enum CourseLevel {
        BASIC('1'), MEDIUM('2'), SENIOR('3'), ADVANCED('4');
        private final char level;

        /**
         * Default constructor of {@link CourseLevel}, with level specified.
         *
         * @param level the level.
         */
        CourseLevel(final char level) {
            this.level = level;
        }

        public static CourseLevel levelOf(final char level) throws ApiServiceException {
            final CourseLevel[] vals = CourseLevel.values();
            for (CourseLevel courseLevel : vals) {
                if (courseLevel.level == level) {
                    return courseLevel;
                }
            }

            log.error("==> No course level found according to the level: {}", level);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    enum SignType {
        MD5("MD5"), HMAC_SHA256("HMAC-SHA256");
        private final String type;

        SignType(final String type) {
            this.type = type;
        }


        public String getType() {
            return type;
        }
    }

    enum TradeType {
        JSAPI, NATIVE, APP;
    }

    /**
     * Enumerate all available order status.
     */
    enum OrderStatus {
        UN_PAID('0'), PAID('1'), REFUND('2'), CANCEL('3'), ANY('9');
        private char code;

        OrderStatus(final char code) {
            this.code = code;
        }

        public static OrderStatus orderStatus(final char code) {
            final OrderStatus[] orderStatuses = OrderStatus.values();
            for (int index = 0; index < orderStatuses.length; index++) {
                if (orderStatuses[index].code == code) {
                    return orderStatuses[index];
                }
            }

            throw new RuntimeException("code " + code + " is not recognized as order status");
        }

        public char getCode() {
            return code;
        }
    }

    enum ProductType {
        COURSE((short) 0), SPORTS_GOODS((short) 1);
        private final short code;

        ProductType(short code) {this.code = code;}

        public static ProductType codeOf(final short code) {
            final ProductType[] prodTypes = ProductType.values();
            for (ProductType productType : prodTypes) {
                if (productType.code == code) {
                    return productType;
                }
            }

            throw new RuntimeException("==> Product Type " + code + " is not supported");
        }

        public short getCode() {
            return code;
        }
    }

    /**
     * 课程预约状态
     */
    enum ClassAppointStatus {
        CANCEL(0, "取消预约"),
        APPOINTED(1, "已预约课程"),
        ATTEND(2, "确认上课"),
        ;
        private int code;
        private String desc;

        ClassAppointStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }
    }

}
