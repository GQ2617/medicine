package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.SaleShopcartDao;
import com.zgq.medicine.entity.SaleShopcart;
import com.zgq.medicine.service.SaleShopcartService;
import org.springframework.stereotype.Service;

/**
 * 购物车 (SaleShopcart)表服务实现类
 *
 * @author makejava
 * @since 2023-05-16 08:58:28
 */
@Service("saleShopcartService")
public class SaleShopcartServiceImpl extends ServiceImpl<SaleShopcartDao, SaleShopcart> implements SaleShopcartService {

}

