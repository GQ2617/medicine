package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductInfoCategoryDao;
import com.zgq.medicine.entity.ProductInfoCategory;
import com.zgq.medicine.service.ProductInfoCategoryService;
import org.springframework.stereotype.Service;

/**
 * 产品分类关系表 (ProductInfoCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 16:43:16
 */
@Service("productInfoCategoryService")
public class ProductInfoCategoryServiceImpl extends ServiceImpl<ProductInfoCategoryDao, ProductInfoCategory> implements ProductInfoCategoryService {

}

