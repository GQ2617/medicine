package com.zgq.medicine.vo;

import com.zgq.medicine.entity.PlatformAddress;
import com.zgq.medicine.entity.PlatformUser;
import com.zgq.medicine.entity.SaleOrder;
import com.zgq.medicine.entity.SaleOrderDetail;
import lombok.Data;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/12 21:59
 * Description:
 */
@Data
public class OrderVo extends SaleOrder {
    PlatformAddress address;
    PlatformUser user;
    List<SaleOrderDetail> orderDetails;
}
