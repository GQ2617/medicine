package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductRecommendDao;
import com.zgq.medicine.entity.ProductRecommend;
import com.zgq.medicine.service.ProductRecommendService;
import org.springframework.stereotype.Service;

/**
 * (ProductRecommend)表服务实现类
 *
 * @author makejava
 * @since 2023-05-13 15:18:19
 */
@Service("productRecommendService")
public class ProductRecommendServiceImpl extends ServiceImpl<ProductRecommendDao, ProductRecommend> implements ProductRecommendService {

}

