package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.PlatformAdminDao;
import com.zgq.medicine.entity.PlatformAdmin;
import com.zgq.medicine.service.PlatformAdminService;
import org.springframework.stereotype.Service;

/**
 * 平台管理员表 (PlatformAdmin)表服务实现类
 *
 * @author makejava
 * @since 2023-05-11 09:13:08
 */
@Service("platformAdminService")
public class PlatformAdminServiceImpl extends ServiceImpl<PlatformAdminDao, PlatformAdmin> implements PlatformAdminService {

}

