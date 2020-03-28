package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Klass;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.KlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * File: ClassController
 * Author: Huanghz
 * Created: 2020/3/28
 * Description:class management
 * CopyRight: All rights reserved
 **/
@Controller
public class KlassController {

    private final KlassService klassService;

    @Autowired
    public KlassController(@Qualifier("klassService") final KlassService klassService) {
        this.klassService = klassService;
    }

    @ResponseBody
    @GetMapping("/api/getAllClass")
    public Response<List<Klass>> getAllclass() throws Exception {
        try {
            final List<Klass> klass = klassService.getAllClass();
            return new Response<List<Klass>>().status(Response.SUCCESS).data(klass);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<List<Klass>>().status(Response.FAIL);
        }
    }
}
