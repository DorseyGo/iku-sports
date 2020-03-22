/**
 * File: CourseRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/21 23:29
 * CopyRight: All rights reserved
 */
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("courseRepository")
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor {

}
