package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.Goods;
import com.iku.sports.mini.admin.model.Paging;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品 controller
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/api/goods/list")
    public Response<Paging<Goods>> goods(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Paging<Goods> paging = new Paging<>(pageNum, pageSize);
        Paging<Goods> goodsPaging = goodsService.queryPage(paging);
        return Response.ok(goodsPaging);
    }
}
