package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.ArticleContent;

import java.util.List;

public interface ArticleContentService {
    List<ArticleContent> getArticleContentsByArticleId(String articleId, int pageNo, int pageSize) throws Exception;
}
