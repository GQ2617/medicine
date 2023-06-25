package com.zgq.medicine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zgq.medicine.entity.ProductHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 浏览记录表(ProductHistory)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-15 20:46:30
 */
@Mapper
public interface ProductHistoryDao extends BaseMapper<ProductHistory> {

}

