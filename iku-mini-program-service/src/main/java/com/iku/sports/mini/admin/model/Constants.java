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

/**
 * The constants which enumerates all available constants used in entire project.
 */
public interface Constants {
    String FORWARD_SLASH = "/";
    int OK_REQ = 0;
    int FAIL_REQ = -1;

    /* retain how many numbers */
    int DIVIDE_SCALE = 2;
    String SUCCESS = "SUCCESS";
    int LEN_32 = 32;
    String PREFIX_PREPAY_ID = "prepay_id=";
    String OK = "OK";
    String FAIL = "FAIL";

    int DEFAULT_PAGE_SIZE = 5;

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
}
