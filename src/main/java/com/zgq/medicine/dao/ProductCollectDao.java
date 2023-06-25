package com.zgq.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgq.medicine.entity.ProductCollect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收藏表(ProductCollect)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-14 08:43:37
 */
@Mapper
public interface ProductCollectDao extends BaseMapper<ProductCollect> {

}

