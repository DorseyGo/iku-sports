package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

@Data
@Builder
public class Article {
    private int id ;
    private String title;
    private String cover;
    private Date createTime;
    private String author;
    private short categoryId;

    @Tolerate
    public Article(){}
}
