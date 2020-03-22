/**
 * File: Course
 * Author: DorSey Q F TANG
 * Created: 2020/3/22 08:48
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "num_classes")
    private int numClasses;

    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "kind_id")
    private CourseKind courseKind;
}
