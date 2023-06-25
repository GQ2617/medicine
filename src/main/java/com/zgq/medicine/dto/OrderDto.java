package com.zgq.medicine.dto;

import com.zgq.medicine.entity.SaleOrder;
import com.zgq.medicine.entity.SaleOrderDetail;
import com.zgq.medicine.entity.SaleShopcart;
import lombok.Data;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/19 20:24
 * Description:
 */
@Data
public class OrderDto extends SaleOrder {
    List<SaleOrderDetail> orderDetails;
}
