/*
 * File: NewOrderRequest
 * Author: DorSey Q F TANG
 * Created: 2020/4/22
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
@Data
public class NewOrderRequest {

    /* who new this order */
    @JsonProperty("token")
    @NotNull
    private String token;

    /* product */
    @JsonProperty("course_id")
    @Min(1)
    private short courseId;

    @JsonProperty("fee")
    @DecimalMin(value = "0.01", inclusive = true)
    private double fee;

    @JsonProperty("discount")
    private float discount;
}
