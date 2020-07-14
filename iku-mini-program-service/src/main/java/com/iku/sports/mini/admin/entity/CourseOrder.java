/*
 * File: CourseOrder
 * Author: DorSey Q F TANG
 * Created: 2020/7/14
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The course order which represents the view that combines order and course.
 */
@Data
public class CourseOrder {

    private String orderId;
    private String userId;
    private String courseName;
    private BigDecimal fee;
    private String courseDescription;
    private char status;
    private String category;
    private char level;

    // computed according to fee
    private String decimalPart = "00";
    private String integerPart = "0";

}
