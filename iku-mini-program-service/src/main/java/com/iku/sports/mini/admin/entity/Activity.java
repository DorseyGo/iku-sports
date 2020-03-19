/**
 * File: Activity
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Activity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "image")
    private String image;

    @Column(name = "link")
    private String link;

    @Column(name = "create_time")
    private Date createTime;

}
