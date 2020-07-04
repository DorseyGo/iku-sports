package com.iku.sports.mini.admin.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtil {
    private static final int centFactor = 100;

    /**
     * 缺省的取整模式，为{@link RoundingMode#HALF_EVEN}
     * （四舍五入，当小数为0.5时，则取最近的偶数）。
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    /**
     * 元转分
     * @param amount 金额，单位：元
     * @return
     */
    public long toCent(double amount) {
        return Math.round(amount * centFactor);
    }

    /**
     * 元转分
     * @param amount 金额，单位：元
     * @return
     */
    public long toCent(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        return rounding(bigDecimal.movePointRight(centFactor), DEFAULT_ROUNDING_MODE);
    }

    /**
     * 对BigDecimal型的值按指定取整方式取整。
     *
     * @param val          待取整的BigDecimal值
     * @param roundingMode 取整方式
     * @return 取整后的long型值
     */
    protected long rounding(BigDecimal val, RoundingMode roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }
}
