package com.zgq.medicine.vo;

import com.zgq.medicine.entity.ProductRecommend;
import com.zgq.medicine.entity.ProductRecommendInfo;
import lombok.Data;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/13 15:21
 * Description:
 */
@Data
public class RecommendVo extends ProductRecommend {
    List<ProductRecommendInfo> recommendInfos;
}
