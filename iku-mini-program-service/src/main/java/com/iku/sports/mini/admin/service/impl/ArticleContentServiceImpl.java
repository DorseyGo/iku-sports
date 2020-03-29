package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Article;

import com.iku.sports.mini.admin.entity.ArticleContent;
import com.iku.sports.mini.admin.repository.ArticleContentRepository;
import com.iku.sports.mini.admin.service.ArticleContentService;
import com.iku.sports.mini.admin.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleContentService")
public class ArticleContentServiceImpl implements ArticleContentService {
    private final ArticleContentRepository articleContentRepository;
    @Autowired
    public ArticleContentServiceImpl(@Qualifier("articleContentRepository") ArticleContentRepository articleContentRepository) {
        this.articleContentRepository = articleContentRepository;
    }

    public List<ArticleContent> getArticleContentsByArticleId(String articleId, int pageNo, int pageSize) throws Exception {
        return articleContentRepository.getArticleContentsByArticleId(articleId,pageNo,pageSize);
    }
}
