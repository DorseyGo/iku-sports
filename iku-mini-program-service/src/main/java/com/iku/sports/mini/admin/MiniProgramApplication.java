/**
 * File: MiniProgramApplication
 * Author: DorSey Q F TANG
 * Created: 2020/3/19
 * CopyRight: All rights reserved
 **/
package com.iku.sports.mini.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.iku.sports.mini.admin.repository"})
@SpringBootApplication
public class MiniProgramApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniProgramApplication.class, args);
    }
}
