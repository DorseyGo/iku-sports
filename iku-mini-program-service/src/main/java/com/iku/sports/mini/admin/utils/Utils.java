/**
 * File: Utils
 * Author: DorSey Q F TANG
 * Created: 2020/4/5 10:51
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * The utility class.
 */
public class Utils {

    /**
     * Join the <tt>prefix</tt> with <tt>suffix</tt> via delimiter.
     * <p>
     *     For example:
     *     <ul>
     *         <li>prefix: http://localhost:9090/</li>
     *         <li>suffix: /api/services</li>
     *         <li>delimiter: /</li>
     *     </ul>
     *     then after called, <tt>http://localhost:9090/api/services</tt> will be returned.
     * </p>
     *
     * @param prefix the prefix
     * @param suffix the suffix
     * @param delim the delimiter
     * @return the joiner, with prefix and suffix joined with delimiter.
     */
    public static String join(final String prefix, final String suffix, final String delim) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(prefix), "Prefix missed");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(suffix), "suffix missed");

        return prefix.replaceAll(delim + "$", "")
                + delim + suffix.replaceAll("^" + delim, "");
    }

}
