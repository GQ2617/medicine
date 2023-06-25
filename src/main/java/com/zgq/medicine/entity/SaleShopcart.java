package com.zgq.medicine.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车 (SaleShopcart)表实体类
 *
 * @author makejava
 * @since 2023-05-16 08:58:28
 */
@SuppressWarnings("serial")
@Data
@ApiModel("购物车实体类")
public class SaleShopcart extends Model<SaleShopcart> {
    // 购物车id
    private Integer id;
    // 用户id
    private Integer userId;
    // 产品id
    private Integer productId;
    // 产品数量
    private Integer productCount;
    // 产品价格
    private Double productPrice;
    // 产品名称
    private String productName;
    // 产品图片
    private String productImage;
    // 生产厂家
    private String productProduction;
    // 产品描述
    private String productDesc;
    // 创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    // 更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;


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

