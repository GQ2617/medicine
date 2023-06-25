package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductRecommendInfoDao;
import com.zgq.medicine.entity.ProductRecommendInfo;
import com.zgq.medicine.service.ProductRecommendInfoService;
import org.springframework.stereotype.Service;

/**
 * (ProductRecommendInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-05-13 15:18:34
 */
@Service("productRecommendInfoService")
public class ProductRecommendInfoServiceImpl extends ServiceImpl<ProductRecommendInfoDao, ProductRecommendInfo> implements ProductRecommendInfoService {

}

