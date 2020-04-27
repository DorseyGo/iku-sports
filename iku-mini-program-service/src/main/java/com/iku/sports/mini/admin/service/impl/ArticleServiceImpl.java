package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Article;
import com.iku.sports.mini.admin.exception.ApiInvokedException;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.ActicleRepository;
import com.iku.sports.mini.admin.service.ArticleService;
import com.iku.sports.mini.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private final ActicleRepository acticleRepository;
    private final IkuSportsConfig config;

    @Autowired
    public ArticleServiceImpl(@Qualifier("articleRepository") ActicleRepository acticleRepository,
            IkuSportsConfig config) {
        this.acticleRepository = acticleRepository;
        this.config = config;
    }

    public List<Article> getArticlesByCategoryId(String categoryId, int pageNo, int pageSize) throws Exception {
        return Optional.ofNullable(acticleRepository.getArticlesByCategoryId(categoryId, pageNo, pageSize))
                .orElseThrow(() -> new ApiServiceException(IkuSportsError.REQ_RESOURCE_NOT_FOUND_ERR));
    }

    @Override
    public List<Article> getFirst3Articles() throws ApiInvokedException {
        final List<Article> articles = acticleRepository.findFirst3Articles();
        articles.forEach(article -> {
            article.setCover(Utils.join(config.getStaticResourceServer(), article.getCover(), Constants.FORWARD_SLASH));
        });

        return articles;
    }
}
