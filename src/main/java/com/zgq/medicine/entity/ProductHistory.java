package com.zgq.medicine.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 浏览记录表(ProductHistory)表实体类
 *
 * @author makejava
 * @since 2023-06-15 20:46:30
 */
@SuppressWarnings("serial")
@Data
@ApiModel("浏览记录表实体类")
public class ProductHistory extends Model<ProductHistory> {
    // id
    @ApiModelProperty("id")
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

