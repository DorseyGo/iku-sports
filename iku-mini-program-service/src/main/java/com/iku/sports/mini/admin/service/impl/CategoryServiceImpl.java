/**
 * File: CategoryServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.repository.CategoryRepository;
import com.iku.sports.mini.admin.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private final LoadingCache<String, List<Category>> cache;
    private static final String KEY_DEF = "categories";
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(@Qualifier("categoryRepository") final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        cache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<Category>>() {
            @Override
            public List<Category> load(String key) throws Exception {
                return createValue(key);
            }
        });
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return cache.get(KEY_DEF);
        } catch (ExecutionException e) {
            log.error("Failed to fetch all categories", e);
            return Collections.emptyList();
        }
    }

    private List<Category> createValue(final String key) {
        final List<Category> categories = categoryRepository.findAll();
        Collections.sort(categories);

        return categories;
    }

    @Override
    public Category getCategoryById(final short categoryId) {
        final List<Category> categories = getAllCategories();
        final Optional<Category> cate = categories.stream().filter(category -> categoryId == category.getId()).findFirst();

        return cate.get();
    }
}
