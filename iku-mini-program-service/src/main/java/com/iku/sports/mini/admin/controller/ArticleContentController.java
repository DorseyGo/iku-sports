package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.ArticleContent;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.ArticleContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleContentController {
    private final ArticleContentService articleContentService;
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    public ArticleContentController(@Qualifier("articleContentService") final ArticleContentService articleContentService){
        this.articleContentService = articleContentService;
    }

    @ResponseBody
    @GetMapping("/api/articleContents")
    public Response<List<ArticleContent>> getArticleContentsByArticleId(String articleId, int pageNo, int pageSize)  {
        try {
            List<ArticleContent> ArticleContents = articleContentService.getArticleContentsByArticleId(articleId,pageNo,pageSize);
            return new Response<List<ArticleContent>>().status(Response.SUCCESS).data(ArticleContents);
        } catch (Exception e) {
            logger.error("Fail to getArticleContentsByArticleId.",e);
            return new Response<List<ArticleContent>>().status(Response.FAIL);
        }
    }
}
