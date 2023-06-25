package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.SaleOrderDetailDao;
import com.zgq.medicine.entity.SaleOrderDetail;
import com.zgq.medicine.service.SaleOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 销售订单详情表 (SaleOrderDetail)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 21:42:57
 */
@Service("saleOrderDetailService")
public class SaleOrderDetailServiceImpl extends ServiceImpl<SaleOrderDetailDao, SaleOrderDetail> implements SaleOrderDetailService {

}

