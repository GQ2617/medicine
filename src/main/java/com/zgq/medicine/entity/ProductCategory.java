package com.zgq.medicine.entity;

import java.time.LocalDateTime;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 分类表 (ProductCategory)表实体类
 *
 * @author makejava
 * @since 2023-05-12 13:53:37
 */
@SuppressWarnings("serial")
@Data
@ApiModel("产品分类实体类")
@EqualsAndHashCode(callSuper = false)
public class ProductCategory extends Model<ProductCategory> {
    // 分类id
    @ApiModelProperty("分类id")
    private Integer id;
    // 分类名称
    @ApiModelProperty("分类名称")
    @Excel(name = "分类名称", width = 30)
    private String name;
    // 分类简介
    @ApiModelProperty("分类简介")
    @Excel(name = "分类简介", width = 30)
    private String slogan;
    // 分类排序
    @ApiModelProperty("分类排序")
    @Excel(name = "分类排序", width = 15)
    private Integer sort;
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

