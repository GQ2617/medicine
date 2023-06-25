package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.ProductRecommend;
import com.zgq.medicine.entity.ProductRecommendInfo;
import com.zgq.medicine.service.ProductRecommendInfoService;
import com.zgq.medicine.service.ProductRecommendService;
import com.zgq.medicine.utils.RedisUtil;
import com.zgq.medicine.vo.RecommendVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Author: zgq
 * Create: 2023/5/13 15:19
 * Description:
 */
@RestController
@RequestMapping("/recommend")
@CrossOrigin
@Transactional
@Api(tags = "推荐模块")
public class ProductRecommendController {

    @Autowired
    private ProductRecommendService recommendService;
    @Autowired
    private ProductRecommendInfoService infoService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping
    @ApiOperation("获取推荐列表")
    public R get() {

        // 查询redis是否有推荐列表数据
        String key = "recommendList";
        List<RecommendVo> recommendList = redisUtil.getList(key, RecommendVo.class);
        if (recommendList != null) {
            // 找到后，重置超时时间，清楚策略为LRU（最近最少使用），不重置清楚策略为FIFO（先进先出）
            redisUtil.expire(key, 1, TimeUnit.HOURS);
            return R.success(recommendList);
        }

        // 查询推荐分类
        List<ProductRecommend> recommends = recommendService.list();
        recommendList = recommends.stream().map((item) -> {
            RecommendVo recommendVo = new RecommendVo();
            BeanUtils.copyProperties(item, recommendVo);
            // 查询推荐产品
            Integer recommendId = item.getId();
            LambdaQueryWrapper<ProductRecommendInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProductRecommendInfo::getRecommendId, recommendId);
            List<ProductRecommendInfo> infos = infoService.list(queryWrapper);
            recommendVo.setRecommendInfos(infos);
            // 保存推荐产品
            return recommendVo;
        }).collect(Collectors.toList());
        redisUtil.add(key, recommendList, 1, TimeUnit.HOURS);

        return R.success(recommendList);
    }


    @PutMapping
    @ApiOperation("保存推荐列表")
    public R update(@RequestBody List<RecommendVo> recommendVos) {
        for (RecommendVo recommendVo : recommendVos) {
            // 保存推荐分类
            recommendService.updateById(recommendVo);
            // 保存推荐产品
            List<ProductRecommendInfo> recommendInfos = recommendVo.getRecommendInfos();
            for (ProductRecommendInfo recommendInfo : recommendInfos) {
                infoService.updateById(recommendInfo);
            }
        }
        String key = "recommendList";
        redisUtil.delete(key);
        return R.success(null);
    }
}
