package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getArticlesByCategoryId(String categoryId, int pageNo, int pageSize) throws Exception;
}
