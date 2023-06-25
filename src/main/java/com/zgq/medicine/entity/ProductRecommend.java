package com.zgq.medicine.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (ProductRecommend)表实体类
 *
 * @author makejava
 * @since 2023-05-13 15:18:19
 */
@SuppressWarnings("serial")
@Data
public class ProductRecommend extends Model<ProductRecommend> {

    private Integer id;

    private String categoryName;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

