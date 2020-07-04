/**
 * File: CategoryController
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.annotation.WebLog;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(@Qualifier("categoryService") final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns a list of all categories.
     *
     * @return all categories.
     * @throws ApiServiceException if any errors detected during process.
     */
    @WebLog(response = false)
    @GetMapping("/categories")
    public Response<List<Category>> getAllCategories() throws ApiServiceException {
        final List<Category> categories = categoryService.getAllCategories();

        return Response.ok(categories);
    }

    @WebLog(response = false)
    @GetMapping("/categories/{categoryId}")
    public Response<Category> getCategoryById(@PathVariable final short categoryId) throws ApiServiceException {
        Category category = categoryService.getCategoryById(categoryId);
        return Response.ok(category);
    }
}
