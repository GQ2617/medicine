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
 * 平台用户地址表 (PlatformAddress)表实体类
 *
 * @author makejava
 * @since 2023-05-12 21:43:31
 */
@SuppressWarnings("serial")
@Data
@ApiModel("用户地址实体类")
public class PlatformAddress extends Model<PlatformAddress> {
    // 地址id
    private Integer id;
    // 用户id
    private Integer userId;
    // 收件人姓名
    private String receiverName;
    // 收件人电话
    private String receiverPhone;
    // 省份
    private String province;
    // 城市
    private String city;
    // 地区
    private String area;
    // 详细地址
    private String addr;
    // 创建时间
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

