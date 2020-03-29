package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Article;
import com.iku.sports.mini.admin.repository.ActicleRepository;
import com.iku.sports.mini.admin.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private final ActicleRepository acticleRepository;

    @Autowired
    public ArticleServiceImpl(@Qualifier("articleRepository") ActicleRepository acticleRepository) {
        this.acticleRepository = acticleRepository;
    }

    public List<Article> getArticlesByCategoryId(String categoryId, int pageNo, int pageSize) throws Exception {
        return acticleRepository.getArticlesByCategoryId(categoryId,pageNo,pageSize);
    }
}
