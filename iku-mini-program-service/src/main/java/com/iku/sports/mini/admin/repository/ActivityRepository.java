/**
 * File: ActivityRepository
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin.repository;

import com.iku.sports.mini.admin.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("activityRepository")
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findFirst3ByOrderByCreateTimeDesc();

    List<Activity> findFirst3ByCategoryIdOrderByCreateTimeDesc(final short categoryId);
}
