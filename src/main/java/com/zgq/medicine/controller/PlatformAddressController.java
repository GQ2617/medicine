package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.PlatformAddress;
import com.zgq.medicine.service.PlatformAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: zgq
 * Create: 2023/5/19 16:24
 * Description:
 */
@RestController
@RequestMapping("/address")
@Api(tags = "地址模块")
public class PlatformAddressController {
    @Autowired
    private PlatformAddressService addressService;

    @GetMapping("/user/{id}")
    @ApiOperation("用户id获取地址列表")
    public R getByUserId(@PathVariable("id") Integer id) {
        return R.success(addressService.list(new LambdaQueryWrapper<PlatformAddress>().eq(PlatformAddress::getUserId, id)));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取地址")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(addressService.getById(id));
    }

    @PostMapping
    @ApiOperation("添加地址")
    public R add(@RequestBody PlatformAddress address) {
        return R.success(addressService.save(address));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除地址")
    public R remove(@PathVariable("id") Integer id) {
        return R.success(addressService.removeById(id));
    }

    @PutMapping
    @ApiOperation("修改地址")
    public R update(@RequestBody PlatformAddress address) {
        return R.success(addressService.updateById(address));
    }
}
