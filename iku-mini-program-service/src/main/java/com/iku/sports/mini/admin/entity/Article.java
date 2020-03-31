package com.iku.sports.mini.admin.entity;

import lombok.Data;
import lombok.experimental.Tolerate;

import java.sql.Timestamp;

@Data
public class Article {
    private int id ;
    private String title;
    private String cover;
    private Timestamp createTime;
    private String author;
    private String categoryId;

    @Tolerate
    public Article(){}
}