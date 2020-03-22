/**
 * File: CourseKindRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/22 11:03
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.CourseKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("courseKindRepository")
public interface CourseKindRepository extends JpaRepository<CourseKind, Short> {

    List<CourseKind> findByCategoryId(final short categoryId);
}
