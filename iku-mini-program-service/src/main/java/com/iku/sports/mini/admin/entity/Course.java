/**
 * File: Course
 * Author: DorSey Q F TANG
 * Created: 2020/3/28 14:58
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.math.BigDecimal;

@Data
public class Course {

    // -------------------------
    // Fields
    // -------------------------
    private short id;
    private String name;
    private char level;
    private BigDecimal fee;
    private int numClasses;

    // ------------------------
    // description collected only when details required.
    private String description;
}
