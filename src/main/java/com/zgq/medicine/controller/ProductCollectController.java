package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.Product;
import com.zgq.medicine.entity.ProductCollect;
import com.zgq.medicine.service.ProductCollectService;
import com.zgq.medicine.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: zgq
 * Create: 2023/6/14 8:46
 * Description:
 */
@RestController
@RequestMapping("/api/collect")
@RequiredArgsConstructor
@CrossOrigin
@Api(tags = "产品收藏模块")
public class ProductCollectController {
    private final ProductCollectService collectService;
    private final ProductService productService;


    @GetMapping("/{id}")
    @ApiOperation("用户id获取收藏列表")
    public R getAll(@PathVariable("id") Integer id) {
        List<ProductCollect> list = collectService.list(new LambdaQueryWrapper<ProductCollect>().eq(ProductCollect::getUserId, id));
        list = list.stream().map(item -> {
            Integer productId = item.getProductId();
            Product product = productService.getById(productId);
            item.setProduct(product);
            return item;
        }).collect(Collectors.toList());

        return R.success(list);
    }

    @PostMapping
    @ApiOperation("加入收藏")
    public R save(@RequestBody ProductCollect productCollect) {
        return R.success(collectService.save(productCollect));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("移除收藏")
    public R remove(@PathVariable("id") Integer id) {
        return R.success(collectService.removeById(id));
    }
}
