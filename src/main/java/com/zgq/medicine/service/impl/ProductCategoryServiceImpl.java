package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductCategoryDao;
import com.zgq.medicine.entity.ProductCategory;
import com.zgq.medicine.service.ProductCategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类表 (ProductCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 13:53:37
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategory> implements ProductCategoryService {

}

