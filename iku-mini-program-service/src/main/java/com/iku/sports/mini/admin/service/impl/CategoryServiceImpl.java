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
import com.iku.sports.mini.admin.config.IkuSportsConfig;
import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.exception.ApiServiceException;
import com.iku.sports.mini.admin.exception.IkuSportsError;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.CategoryRepository;
import com.iku.sports.mini.admin.service.CategoryService;
import com.iku.sports.mini.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private final LoadingCache<String, List<Category>> cache;
    private static final String KEY_DEF = "categories";
    private final CategoryRepository categoryRepository;
    private final IkuSportsConfig config;

    @Autowired
    public CategoryServiceImpl(@Qualifier("categoryRepository") final CategoryRepository categoryRepository,
            IkuSportsConfig config) {
        this.categoryRepository = categoryRepository;
        this.config = config;
        cache = CacheBuilder.newBuilder().expireAfterAccess(config.getExpiryInDays(), TimeUnit.DAYS)
                .expireAfterWrite(config.getExpiryInDays(), TimeUnit.DAYS)
                .build(new CacheLoader<String, List<Category>>() {
                    @Override
                    public List<Category> load(String key) throws Exception {
                        return createValue(key);
                    }
                });
    }

    @Override
    public List<Category> getAllCategories() throws ApiServiceException {
        try {
            return cache.get(KEY_DEF);
        } catch (ExecutionException e) {
            log.error("==> Failed to fetch all categories", e);
            throw new ApiServiceException(IkuSportsError.INTERNAL_ERR);
        }
    }

    private List<Category> createValue(final String key) throws DataAccessException {
        List<Category> categories;
        try {
            categories = categoryRepository.findAll();
        } catch (DataAccessException e) {
            log.error("==> Failed to find all categories", e);
            throw e;
        }

        Collections.sort(categories);

        return categories;
    }

    @Override
    public Category getCategoryById(final short categoryId) throws ApiServiceException {
        final List<Category> categories = getAllCategories();
        final Optional<Category> cate = categories.stream()
                .filter(category -> categoryId == category.getId())
                .findFirst();

        return cate.get();
    }
}
