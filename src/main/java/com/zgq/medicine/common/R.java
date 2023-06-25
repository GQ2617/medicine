package com.zgq.medicine.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: zgq
 * Create: 2023/5/11 9:28
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回结果")
public class R<T> {

    @ApiModelProperty("状态码")
    private Integer code; // 编码：0成功，1和其它数字为失败

    @ApiModelProperty("错误信息")
    private String msg; // 错误信息

    @ApiModelProperty("数据")
    private T data; // 数据

    @ApiModelProperty("动态数据")
    private Map map = new HashMap(); // 动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 0;
        r.msg = "success";
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 1;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}