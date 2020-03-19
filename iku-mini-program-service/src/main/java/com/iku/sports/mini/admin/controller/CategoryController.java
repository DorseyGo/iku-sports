/**
 * File: CategoryController
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(@Qualifier("categoryService") final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseBody
    @GetMapping("/api/categories")
    public Response<Category> getAllCategories() {
        final List<Category> categories = categoryService.getAllCategories();

        return new Response<Category>().status(Response.SUCCESS).data(categories);
    }
}
