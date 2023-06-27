package com.zgq.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.Product;
import com.zgq.medicine.entity.ProductHistory;
import com.zgq.medicine.service.ProductHistoryService;
import com.zgq.medicine.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 浏览记录表(ProductHistory)表控制层
 *
 * @author makejava
 * @since 2023-06-16 08:33:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/productHistory")
@Api(tags = "浏览记录表")
public class ProductHistoryController {
    private final ProductHistoryService productHistoryService;
    private final ProductService productService;

    @GetMapping("/{userId}")
    @ApiOperation("获取全部记录")
    public R selectAll(@PathVariable("userId") Integer userId) {

        LambdaQueryWrapper<ProductHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductHistory::getUserId, userId);
        queryWrapper.orderByDesc(ProductHistory::getId);
        List<ProductHistory> productHistoryList = productHistoryService.list(queryWrapper);
        productHistoryList = productHistoryList.stream().map(item -> {
            Integer productId = item.getProductId();
            Product product = productService.getById(productId);
            item.setProduct(product);
            return item;
        }).collect(Collectors.toList());
        return R.success(productHistoryList);
    }


    @PostMapping
    @ApiOperation("新增浏览记录")
    public R save(@RequestBody ProductHistory productHistory) {
        LambdaQueryWrapper<ProductHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductHistory::getUserId, productHistory.getUserId());
        queryWrapper.eq(ProductHistory::getProductId, productHistory.getProductId());
        ProductHistory history = productHistoryService.getOne(queryWrapper);
        if (history != null) {
            productHistoryService.remove(queryWrapper);
        }
        return R.success(productHistoryService.save(productHistory));
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除浏览记录")
    public R delete(@PathVariable("id") Integer id) {
        return R.success(productHistoryService.removeById(id));
    }
}

