package com.zgq.medicine.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * (ProductCarousel)表实体类
 *
 * @author makejava
 * @since 2023-05-13 14:08:23
 */
@SuppressWarnings("serial")
@Data
@ApiModel("轮播图实体类")
public class ProductCarousel extends Model<ProductCarousel> {

    private Integer id;

    private Integer productId;

    private String productImage;


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

