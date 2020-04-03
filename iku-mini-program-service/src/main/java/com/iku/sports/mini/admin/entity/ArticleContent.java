package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.sql.Timestamp;

@Data
@Builder
public class ArticleContent {
    private int id;
    private String material;
    private String MaterialType;
    private Timestamp createTime;
    private int articleId;

    @Tolerate
    public ArticleContent(){}
}
