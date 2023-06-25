package com.zgq.medicine.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 平台管理员表 (PlatformAdmin)表实体类
 *
 * @author makejava
 * @since 2023-05-11 09:10:11
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("管理员实体类")
public class PlatformAdmin extends Model<PlatformAdmin> {
    // 管理员id
    @ApiModelProperty("管理员id")
    private Integer id;
    // 用户名
    @ApiModelProperty("管理员账户")
    private String username;
    // 用户密码
    @ApiModelProperty("管理员密码")
    private String password;
    // 头像
    @ApiModelProperty("管理员头像")
    private String avatar;
    // 昵称
    @ApiModelProperty("管理员昵称")
    private String nickname;
    // 状态 0正常1禁用
    @ApiModelProperty("管理员账号状态")
    private Integer status;
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

