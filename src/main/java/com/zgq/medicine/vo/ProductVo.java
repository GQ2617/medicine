package com.zgq.medicine.vo;

import com.zgq.medicine.entity.Product;
import com.zgq.medicine.entity.ProductCategory;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Author: zgq
 * Create: 2023/5/12 16:49
 * Description:
 */
@Data
@ToString
public class ProductVo extends Product {
    List<ProductCategory> categories;
}
