package com.zgq.medicine.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (ProductRecommendInfo)表实体类
 *
 * @author makejava
 * @since 2023-05-13 15:18:34
 */
@SuppressWarnings("serial")
@Data
public class ProductRecommendInfo extends Model<ProductRecommendInfo> {

    private Integer id;

    private Integer productId;

    private String productImage;

    private Integer recommendId;


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

