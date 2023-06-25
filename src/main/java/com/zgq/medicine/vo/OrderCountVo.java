package com.zgq.medicine.vo;

import lombok.Data;

/**
 * Author: zgq
 * Create: 2023/6/15 15:38
 * Description:
 */
@Data
public class OrderCountVo {
    Long minusOneCount;
    Long zeroCount;
    Long oneCount;
    Long ThreeCount;
    Long collectCount;
    Long historyCount;
}
