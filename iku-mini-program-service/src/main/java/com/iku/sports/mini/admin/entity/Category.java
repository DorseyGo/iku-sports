/**
 * File: Category
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue
    private short id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "icon", unique = false)
    private String icon;

    @Column(name = "sequence")
    private short sequence;

    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Override
    public int compareTo(final Category other) {
        return (sequence == other.sequence) ? 0 : (sequence < other.sequence ? -1 : 1);
    }
}
