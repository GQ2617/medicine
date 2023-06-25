package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductHistoryDao;
import com.zgq.medicine.entity.ProductHistory;
import com.zgq.medicine.service.ProductHistoryService;
import org.springframework.stereotype.Service;

/**
 * 浏览记录表(ProductHistory)表服务实现类
 *
 * @author makejava
 * @since 2023-06-15 20:46:30
 */
@Service("productHistoryService")
public class ProductHistoryServiceImpl extends ServiceImpl<ProductHistoryDao, ProductHistory> implements ProductHistoryService {

}

