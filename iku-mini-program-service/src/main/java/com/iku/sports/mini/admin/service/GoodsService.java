package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.Goods;
import com.iku.sports.mini.admin.model.Paging;

public interface GoodsService {
    Paging<Goods> queryPage(Paging<Goods> pageable);
}
