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
 * 销售订单详情表 (SaleOrderDetail)表实体类
 *
 * @author makejava
 * @since 2023-05-12 21:42:57
 */
@SuppressWarnings("serial")
@Data
@ApiModel("订单详情实体类")
public class SaleOrderDetail extends Model<SaleOrderDetail> {
    // 订单详情id
    private Integer id;
    // 订单id
    private Integer orderId;
    // 产品id
    private Integer productId;
    // 产品名称
    private String productName;
    // 产品价格
    private Double productPrice;
    // 产品数量
    private Integer productCount;
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

