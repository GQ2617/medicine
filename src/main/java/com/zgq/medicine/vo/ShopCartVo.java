package com.zgq.medicine.vo;

import com.zgq.medicine.entity.SaleShopcart;
import lombok.Data;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/17 8:51
 * Description:
 */
@Data
public class ShopCartVo {
    private List<SaleShopcart> saleShopcarts;
    private Integer count;
    private Double amount;
}
