package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.PlatformAddressDao;
import com.zgq.medicine.entity.PlatformAddress;
import com.zgq.medicine.service.PlatformAddressService;
import org.springframework.stereotype.Service;

/**
 * 平台用户地址表 (PlatformAddress)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 21:43:31
 */
@Service("platformAddressService")
public class PlatformAddressServiceImpl extends ServiceImpl<PlatformAddressDao, PlatformAddress> implements PlatformAddressService {

}

