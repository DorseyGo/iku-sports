/**
 * File: CourseKind
 * Author: DorSey Q F TANG
 * Created: 2020/3/22 09:30
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_kind")
public class CourseKind {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private short id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "category_id")
    private short categoryId;
}
