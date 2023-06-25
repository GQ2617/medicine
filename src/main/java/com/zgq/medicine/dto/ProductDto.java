package com.zgq.medicine.dto;

import com.zgq.medicine.entity.Product;
import lombok.Data;
import lombok.ToString;

/**
 * Author: zgq
 * Create: 2023/5/12 20:14
 * Description:
 */
@Data
@ToString
public class ProductDto extends Product {
    Integer[] categories;
}
