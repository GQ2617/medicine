package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductDao;
import com.zgq.medicine.entity.Product;
import com.zgq.medicine.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * 产品表 (Product)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 16:40:12
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {

}

