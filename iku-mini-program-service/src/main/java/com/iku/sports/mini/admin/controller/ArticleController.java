package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Article;
import com.iku.sports.mini.admin.exception.ApiInvokedException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(@Qualifier("articleService") final ArticleService articleService){
        this.articleService = articleService;
    }

    @ResponseBody
    @GetMapping("/api/articles")
    public Response<List<Article>> getArticlesByCategoryId(String categoryId,int pageNo,int pageSize)  {
        try {
            List<Article> Articles = articleService.getArticlesByCategoryId(categoryId,pageNo,pageSize);
            return new Response<List<Article>>().status(Response.SUCCESS).data(Articles);
        } catch (Exception e) {
            log.error("Fail to getArticlesByCategoryId.",e);
            return new Response<List<Article>>().status(Response.FAIL);
        }
    }

    @ResponseBody
    @GetMapping("/api/articles/first3")
    public Response<List<Article>> getFirst3Articles() {
        try {
            final List<Article> articles = articleService.getFirst3Articles();
            return new Response<List<Article>>().status(Response.SUCCESS).data(articles);
        } catch (ApiInvokedException aie) {
            log.error("Failed to fetch first 3 articles", aie);
            return new Response<List<Article>>().status(Response.FAIL);
        }
    }

}
