package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.PlatformUserDao;
import com.zgq.medicine.entity.PlatformUser;
import com.zgq.medicine.service.PlatformUserService;
import org.springframework.stereotype.Service;

/**
 * 平台用户表 (PlatformUser)表服务实现类
 *
 * @author makejava
 * @since 2023-05-12 21:43:47
 */
@Service("platformUserService")
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserDao, PlatformUser> implements PlatformUserService {

}

