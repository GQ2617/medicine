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
 * 产品分类关系表 (ProductInfoCategory)表实体类
 *
 * @author makejava
 * @since 2023-05-12 16:43:16
 */
@SuppressWarnings("serial")
@Data
@ApiModel("产品信息分类关系实体类")
public class ProductInfoCategory extends Model<ProductInfoCategory> {
    // 标识
    @ApiModelProperty("id")
    private Integer id;
    // 产品id
    @ApiModelProperty("产品id")
    private Integer productId;
    // 分类id
    @ApiModelProperty("分类id")
    private Integer categoryId;
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

