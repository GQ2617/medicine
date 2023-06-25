package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.Product;
import com.zgq.medicine.entity.ProductCategory;
import com.zgq.medicine.entity.ProductInfoCategory;
import com.zgq.medicine.entity.ProductRecommendInfo;
import com.zgq.medicine.service.ProductCategoryService;
import com.zgq.medicine.service.ProductInfoCategoryService;
import com.zgq.medicine.service.ProductRecommendInfoService;
import com.zgq.medicine.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Author: zgq
 * Create: 2023/5/12 13:57
 * Description:
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
@Api(tags = "产品分类")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInfoCategoryService infoCategoryService;
    @Autowired
    private ProductRecommendInfoService recommendInfoService;

    /**
     * 获取所有分类
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取所有分类")
    public R getAll() {
        return R.success(categoryService.list());
    }

    /**
     * 分页条件获取分类列表
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping
    @ApiOperation("分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", defaultValue = "5"),
            @ApiImplicitParam(name = "name", value = "分类名称"),
    })
    public R get(Integer page, Integer pageSize, String name) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        // 分页
        Page<ProductCategory> pageInfo = new Page<>(page, pageSize);
        // 条件
        LambdaQueryWrapper<ProductCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name), ProductCategory::getName, name);
        queryWrapper.orderByAsc(ProductCategory::getSort);
        // 查询
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询分类")
    @ApiImplicitParam(name = "id", value = "分类id", required = true)
    public R getById(@PathVariable("id") Integer id) {
        return R.success(categoryService.getById(id));
    }

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    @ApiOperation("添加分类")
    public R save(@RequestBody ProductCategory category) {
        categoryService.save(category);
        return R.success(null);
    }


    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    @ApiImplicitParam(name = "id", value = "分类id", required = true)
    public R remove(@PathVariable("id") Integer id) {
        // 查询分类下是否包含产品
        LambdaQueryWrapper<ProductInfoCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductInfoCategory::getCategoryId, id);
        List<ProductInfoCategory> list = infoCategoryService.list(queryWrapper);
        if (list.size() > 0) {
            return R.error("分类下包含产品，不能删除");
        }
        // 判断分类是否被推荐
        LambdaQueryWrapper<ProductRecommendInfo> recommendInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recommendInfoLambdaQueryWrapper.eq(ProductRecommendInfo::getProductId, id);
        List<ProductRecommendInfo> recommendInfos = recommendInfoService.list(recommendInfoLambdaQueryWrapper);
        if (recommendInfos.size() > 0) {
            return R.error("该分类正在被推荐，不能删除");
        }
        return R.success(categoryService.removeById(id));
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public R edit(@RequestBody ProductCategory category) {
        return R.success(categoryService.updateById(category));
    }

    @GetMapping("/product/{id}")
    @ApiOperation("根据分类id获取产品")
    public R getProd(@PathVariable("id") Integer id) {
        // 查询分类id对应产品id
        LambdaQueryWrapper<ProductInfoCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductInfoCategory::getCategoryId, id);
        List<ProductInfoCategory> list = infoCategoryService.list(queryWrapper);

        // 根据产品id查询产品
        List<Product> products = list.stream().map((item) -> {
            Integer productId = item.getProductId();
            return productService.getById(productId);
        }).collect(Collectors.toList());
        return R.success(products);
    }
}
