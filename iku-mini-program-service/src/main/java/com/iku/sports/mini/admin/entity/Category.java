/**
 * File: Category
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Category implements Comparable<Category> {

    private short id;
    private String name;
    private String icon;
    private short sequence;
    private Date lastModifiedTime;

    @Tolerate
    public Category() {}

    @Override
    public int compareTo(final Category other) {
        return (sequence == other.sequence) ? 0 : (sequence < other.sequence ? -1 : 1);
    }
}
