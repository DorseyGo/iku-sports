package com.iku.sports.mini.admin.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

/**
 * File: CourseClass
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:
 * CopyRight: All rights reserved
 **/
@Data
@Builder
public class CourseClass {
   private int id;
   private String title;
   private int chapter;
   private String content;
   private long watches;
   private int courseId;
   private int coachId;
   private String videoUrl;
   private String cover;

   @Tolerate
   public CourseClass(){};
}
