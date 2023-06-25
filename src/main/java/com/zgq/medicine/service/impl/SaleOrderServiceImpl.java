package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.SaleOrderDao;
import com.zgq.medicine.entity.SaleOrder;
import com.zgq.medicine.service.SaleOrderService;
import org.springframework.stereotype.Service;

/**
 * 销售订单表 (SaleOrder)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 21:36:24
 */
@Service("saleOrderService")
public class SaleOrderServiceImpl extends ServiceImpl<SaleOrderDao, SaleOrder> implements SaleOrderService {

}

