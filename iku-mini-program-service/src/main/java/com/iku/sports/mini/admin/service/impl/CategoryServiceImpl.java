/**
 * File: CategoryServiceImpl
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.repository.CategoryRepository;
import com.iku.sports.mini.admin.service.CategoryService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(@Qualifier("categoryRepository") final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        final List<Category> categories = categoryRepository.findAll();
        Collections.sort(categories);

        return categories;
    }
}
