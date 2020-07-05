/**
 * File: CoursePrepaymentRequest
 * Author: DorSey Q F TANG
 * Created: 2020/7/5 10:48
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The request for prepayment.
 */
@Data
public class CoursePrepaymentRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("courseId")
    private short courseId;

}
