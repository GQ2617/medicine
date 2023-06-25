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
 * 产品表 (Product)表实体类
 *
 * @author makejava
 * @since 2023-05-12 16:40:12
 */
@SuppressWarnings("serial")
@Data
@ApiModel("产品信息实体类")
public class Product extends Model<Product> {
    // 产品id
    @ApiModelProperty("产品id")
    private Integer id;
    // 产品名称
    @ApiModelProperty("产品名称")
    private String name;
    // 产品价格
    @ApiModelProperty("产品价格")
    private Double price;
    // 产品图片
    @ApiModelProperty("产品图片")
    private String image;
    // 生产厂家
    @ApiModelProperty("生产厂家")
    private String production;
    // 产品描述
    @ApiModelProperty("产品描述")
    private String dscp;
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

    @ApiModelProperty("是否轮播")
    private Integer status;


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

