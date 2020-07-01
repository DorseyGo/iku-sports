package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.Goods;
import com.iku.sports.mini.admin.model.Paging;
import com.iku.sports.mini.admin.repository.GoodsRepository;
import com.iku.sports.mini.admin.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Paging<Goods> queryPage(Paging<Goods> pageable) {
        List<Goods> goodsList = goodsRepository.queryPage(pageable);

        int count = goodsRepository.count();
        pageable.setData(goodsList).setTotal(count);

        return pageable;
    }
}
