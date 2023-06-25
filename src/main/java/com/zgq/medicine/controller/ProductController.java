package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgq.medicine.common.R;
import com.zgq.medicine.dto.ProductDto;
import com.zgq.medicine.entity.*;
import com.zgq.medicine.service.*;
import com.zgq.medicine.vo.ProductByCategoryVo;
import com.zgq.medicine.vo.ProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: zgq
 * Create: 2023/5/12 16:45
 * Description:
 */
@RestController
@RequestMapping("/product")
@CrossOrigin
@Transactional
@Api(tags = "产品信息")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInfoCategoryService infoCategoryService;
    @Autowired
    private ProductCategoryService categoryService;
    @Autowired
    private ProductCarouselService carouselService;
    @Autowired
    private ProductRecommendInfoService recommendInfoService;

    @GetMapping
    @ApiOperation("分页查询所有产品")
    public R get(Integer page, Integer pageSize, String name, String production) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        // 分页
        Page<Product> productPage = new Page<>(page, pageSize);
        // 条件
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name), Product::getName, name);
        queryWrapper.like(Strings.isNotEmpty(production), Product::getProduction, production);
        // 查询
        productService.page(productPage, queryWrapper);

        // 整合
        Page<ProductVo> productDtoPage = new Page<>();
        BeanUtils.copyProperties(productPage, productDtoPage, "records");
        // 获取所有产品
        List<Product> records = productPage.getRecords();
        List<ProductVo> list = records.stream().map((item) -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(item, productVo);
            // 获取产品id
            Integer id = item.getId();
            // 根据产品id查询分类id
            LambdaQueryWrapper<ProductInfoCategory> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ProductInfoCategory::getProductId, id);
            List<ProductInfoCategory> infoCategories = infoCategoryService.list(queryWrapper1);

            // 根据分类id查询分类信息
            List<ProductCategory> categoryList = infoCategories.stream().map((item2) -> {
                Integer categoryId = item2.getCategoryId();
                ProductCategory category = categoryService.getById(categoryId);
                return category;
            }).collect(Collectors.toList());

            productVo.setCategories(categoryList);
            return productVo;
        }).collect(Collectors.toList());
        productDtoPage.setRecords(list);
        return R.success(productDtoPage);
    }

    @GetMapping("/list")
    @ApiOperation("产品列表")
    public R getList(String name, String dscp) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(name), Product::getName, name);
        queryWrapper.or().like(Strings.isNotEmpty(dscp), Product::getDscp, dscp);
        return R.success(productService.list(queryWrapper));
    }

    @GetMapping("/{id}")
    @ApiOperation("id获取产品")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(productService.getById(id));
    }

    @PostMapping
    @ApiOperation("添加产品")
    public R save(@RequestBody ProductDto productDto) {
        // 添加产品到产品表
        productService.save(productDto);

        // 获取产品id
        Integer id = productDto.getId();
        // 获取分类id
        Integer[] categoryId = productDto.getCategories();

        // 添加产品id和分类id到关系表
        for (int i = 0; i < categoryId.length; i++) {
            ProductInfoCategory infoCategory = new ProductInfoCategory();
            infoCategory.setProductId(id);
            infoCategory.setCategoryId(categoryId[i]);
            infoCategoryService.save(infoCategory);
        }

        return R.success(null);
    }


    @PutMapping
    @ApiOperation("修改产品")
    public R edit(@RequestBody ProductDto productDto) {
        // 添加产品到产品表
        productService.updateById(productDto);

        // 获取产品id
        Integer id = productDto.getId();

        // 根据产品id删除关系表
        LambdaQueryWrapper<ProductInfoCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductInfoCategory::getProductId, id);
        infoCategoryService.remove(queryWrapper);

        // 获取分类id
        Integer[] categoryId = productDto.getCategories();

        // 添加产品id和分类id到关系表
        for (int i = 0; i < categoryId.length; i++) {
            ProductInfoCategory infoCategory = new ProductInfoCategory();
            infoCategory.setProductId(id);
            infoCategory.setCategoryId(categoryId[i]);
            infoCategoryService.save(infoCategory);
        }

        return R.success(null);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除产品")
    public R remove(@PathVariable("id") Integer id) {
        // 判断产品是否被轮播
        LambdaQueryWrapper<ProductCarousel> carouselLambdaQueryWrapper = new LambdaQueryWrapper<>();
        carouselLambdaQueryWrapper.eq(ProductCarousel::getProductId, id);
        List<ProductCarousel> carousels = carouselService.list(carouselLambdaQueryWrapper);
        if (carousels.size() > 0) {
            return R.error("该产品正在被轮播，不能删除");
        }

        // 判断产品是否被推荐
        LambdaQueryWrapper<ProductRecommendInfo> recommendInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recommendInfoLambdaQueryWrapper.eq(ProductRecommendInfo::getProductId, id);
        List<ProductRecommendInfo> recommendInfos = recommendInfoService.list(recommendInfoLambdaQueryWrapper);
        if (recommendInfos.size() > 0) {
            return R.error("该产品正在被推荐，不能删除");
        }

        // 删除产品
        productService.removeById(id);
        // 删除产品分类关系表
        LambdaQueryWrapper<ProductInfoCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductInfoCategory::getProductId, id);
        infoCategoryService.remove(queryWrapper);
        return R.success(null);
    }

    @GetMapping("/category")
    @ApiOperation("不同分类产品数量")
    public R getCountByCategory() {
        List<ProductCategory> categories = categoryService.list();
        List<ProductByCategoryVo> productCounts = categories.stream().map(category -> {
            ProductByCategoryVo productCount = new ProductByCategoryVo();
            Integer categoryId = category.getId();
            long count = infoCategoryService.count(new LambdaQueryWrapper<ProductInfoCategory>().eq(ProductInfoCategory::getCategoryId, categoryId));
            productCount.setCategory(category.getName());
            productCount.setCount(count);
            return productCount;
        }).collect(Collectors.toList());
        return R.success(productCounts);
    }
}
