package com.zgq.medicine.controller;

import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.ProductCarousel;
import com.zgq.medicine.service.ProductCarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/13 14:09
 * Description:
 */
@RestController
@RequestMapping("/carousel")
@CrossOrigin
@Transactional
@Api(tags = "轮播图模块")
public class ProductCarouselController {
    @Autowired
    private ProductCarouselService carouselService;

    @GetMapping
    @ApiOperation("获取轮播图")
    public R get() {
        return R.success(carouselService.list());
    }

    @PutMapping
    @ApiOperation("设置轮播图")
    public R update(@RequestBody List<ProductCarousel> carousels) {
        for (ProductCarousel carousel : carousels) {
            carouselService.updateById(carousel);
        }
        return R.success(null);
    }
}
