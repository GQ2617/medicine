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
 * 销售订单表 (SaleOrder)表实体类
 *
 * @author makejava
 * @since 2023-05-12 21:36:24
 */
@SuppressWarnings("serial")
@Data
@ApiModel("订单实体类")
@EqualsAndHashCode(callSuper = false)
public class SaleOrder extends Model<SaleOrder> {
    // 订单id
    private Integer id;
    // 订单编号
    @Excel(name = "订单号", width = 30, needMerge = true)
    private String number;
    // 订单状态 0待发货，1待收货，2已收货，3退货中，4已退货，5已拒绝
    @Excel(name = "状态", width = 10, needMerge = true, replace = {"待付款_-1", "待发货_0", "待收货_1", "已收货_2", "退货中_3", "已退货_4", "已拒绝_5"})
    private Integer status;
    // 用户id
    private Integer userId;
    // 地址id
    private Integer addressId;
    // 总金额
    @Excel(name = "订单金额", width = 10, needMerge = true, suffix = "元")
    private Double amount;
    // 创建时间
    @Excel(name = "创建时间", width = 30, needMerge = true, format = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    // 更新时间
    @ApiModelProperty("修改时间")
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

