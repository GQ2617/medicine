package com.zgq.medicine.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 收藏表(ProductCollect)表实体类
 *
 * @author makejava
 * @since 2023-06-14 08:43:37
 */
@SuppressWarnings("serial")
@Data
@ApiModel("收藏表实体类")
public class ProductCollect extends Model<ProductCollect> {
    // 收藏id
    @ApiModelProperty("收藏id")
    private Integer id;
    // 用户id
    @ApiModelProperty("用户id")
    private Integer userId;
    // 产品id
    @ApiModelProperty("产品id")
    private Integer productId;
    // 产品信息
    @TableField(exist = false)
    @ApiModelProperty("产品信息")
    private Product product;


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

