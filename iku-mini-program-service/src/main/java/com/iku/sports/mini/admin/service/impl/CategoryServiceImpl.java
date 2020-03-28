/**
 * File: CategoryServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service.impl;

import com.google.common.collect.Maps;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.repository.CategoryRepository;
import com.iku.sports.mini.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private static final Map<String, List<Category>> CACHE = Maps.newHashMapWithExpectedSize(1);
    private static final String KEY_DEF = "categories";
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(@Qualifier("categoryRepository") final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = CACHE.get(KEY_DEF);
        if (categories != null) {
            return categories;
        }

        categories = categoryRepository.findAll();
        Collections.sort(categories);
        CACHE.put(KEY_DEF, categories);

        return categories;
    }

    @Override
    public Category getCategoryById(final short categoryId) {
        final List<Category> categories = getAllCategories();
        final Optional<Category> cate = categories.stream().filter(category -> categoryId == category.getId()).findFirst();

        return cate.get();
    }
}
