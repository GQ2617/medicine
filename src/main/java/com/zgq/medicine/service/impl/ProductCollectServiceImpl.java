package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductCollectDao;
import com.zgq.medicine.entity.ProductCollect;
import com.zgq.medicine.service.ProductCollectService;
import org.springframework.stereotype.Service;

/**
 * 收藏表(ProductCollect)表服务实现类
 *
 * @author makejava
 * @since 2023-06-14 08:43:37
 */
@Service("productCollectService")
public class ProductCollectServiceImpl extends ServiceImpl<ProductCollectDao, ProductCollect> implements ProductCollectService {

}

