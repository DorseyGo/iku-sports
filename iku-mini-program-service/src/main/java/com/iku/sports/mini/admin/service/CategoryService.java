/**
 * File: CategoryService
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Category;
import com.iku.sports.mini.admin.exception.ApiServiceException;

import java.util.List;

public interface CategoryService {

    /**
     * Returns a list of categories.
     *
     * @return
     */
    List<Category> getAllCategories() throws ApiServiceException;

    /**
     * Retrieves the category according to the category id.
     *
     * @param categoryId the category id.
     * @return the category.
     * @throws ApiServiceException if any SQL error detected.
     */
    Category getCategoryById(final short categoryId) throws ApiServiceException;
}
