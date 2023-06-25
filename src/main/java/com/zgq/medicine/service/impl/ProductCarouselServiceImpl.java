package com.zgq.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zgq.medicine.dao.ProductCarouselDao;
import com.zgq.medicine.entity.ProductCarousel;
import com.zgq.medicine.service.ProductCarouselService;
import org.springframework.stereotype.Service;

/**
 * (ProductCarousel)表服务实现类
 *
 * @author makejava
 * @since 2023-05-13 14:08:23
 */
@Service("productCarouselService")
public class ProductCarouselServiceImpl extends ServiceImpl<ProductCarouselDao, ProductCarousel> implements ProductCarouselService {

}

