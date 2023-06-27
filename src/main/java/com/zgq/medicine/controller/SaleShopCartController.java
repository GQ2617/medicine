package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.SaleShopcart;
import com.zgq.medicine.service.SaleShopcartService;
import com.zgq.medicine.vo.ShopCartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/16 9:01
 * Description:
 */
@RestController
@RequestMapping("/shopcart")
@Api(tags = "购物车模块")
public class SaleShopCartController {
    @Autowired
    private SaleShopcartService shopcartService;

    @GetMapping("/{id}")
    @ApiOperation("获取购物车列表")
    public R getByUser(@PathVariable String id) {
        LambdaQueryWrapper<SaleShopcart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleShopcart::getUserId, id);
        List<SaleShopcart> list = shopcartService.list(queryWrapper);
        ShopCartVo shopCartVo = new ShopCartVo();
        shopCartVo.setSaleShopcarts(list);
        // 计算金额和数量
        Integer count = 0;
        Double amount = 0.0;
        for (SaleShopcart saleShopcart : list) {
            count += saleShopcart.getProductCount();
            amount += saleShopcart.getProductPrice() * saleShopcart.getProductCount();
        }
        shopCartVo.setCount(count);
        shopCartVo.setAmount(amount);
        return R.success(shopCartVo);
    }

    @PostMapping
    @ApiOperation("加入购物车")
    public R add(@RequestBody SaleShopcart saleShopcart) {
        // 查询是否已有
        LambdaQueryWrapper<SaleShopcart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleShopcart::getUserId, saleShopcart.getUserId());
        queryWrapper.eq(SaleShopcart::getProductId, saleShopcart.getProductId());
        SaleShopcart shop = shopcartService.getOne(queryWrapper);
        if (shop == null) {
            shopcartService.save(saleShopcart);
        } else {
            Integer count = shop.getProductCount();
            count++;
            shop.setProductCount(count);
            shopcartService.update(shop, queryWrapper);
        }
        return R.success(null);
    }

    @PostMapping("/addorminus")
    @ApiOperation("增减商品数量")
    public R updateCount(Integer value, Integer id) {
        SaleShopcart shop = shopcartService.getById(id);
        shop.setProductCount(value);
        shopcartService.updateById(shop);
        return R.success(null);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("移除购物车")
    public R remove(@PathVariable String id) {
        shopcartService.removeById(id);
        return R.success(null);
    }
}
