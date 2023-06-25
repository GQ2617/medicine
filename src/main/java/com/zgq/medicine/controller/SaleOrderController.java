package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zgq.medicine.common.R;
import com.zgq.medicine.dto.OrderDto;
import com.zgq.medicine.entity.*;
import com.zgq.medicine.service.*;
import com.zgq.medicine.vo.OrderCountVo;
import com.zgq.medicine.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.RewriteException;
import org.apache.logging.log4j.util.Strings;
import org.simpleframework.xml.Path;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: zgq
 * Create: 2023/5/12 21:48
 * Description:
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
@Transactional
@Api(tags = "订单模块")
public class SaleOrderController {

    @Autowired
    private SaleOrderService orderService;

    @Autowired
    private SaleOrderDetailService orderDetailService;

    @Autowired
    private PlatformAddressService addressService;

    @Autowired
    private PlatformUserService userService;

    @Autowired
    private SaleShopcartService shopcartService;
    @Autowired
    private ProductHistoryService historyService;
    @Autowired
    private ProductCollectService collectService;

    @GetMapping
    @ApiOperation("零售列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "页码"),
            @ApiImplicitParam(name = "name", value = "用户条件"),
            @ApiImplicitParam(name = "status", value = "状态条件"),
            @ApiImplicitParam(name = "code", value = "0零售，1退货", required = true),
    })
    public R get(Integer page, Integer pageSize, String name, String status, Integer code) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        // 分页
        Page<SaleOrder> orderPage = new Page<>(page, pageSize);
        // 条件
        LambdaQueryWrapper<SaleOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (!"".equals(name)) {
            // 获取用户id
            LambdaQueryWrapper<PlatformAddress> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Strings.isNotEmpty(name), PlatformAddress::getReceiverName, name);
            List<PlatformAddress> list = addressService.list(queryWrapper);
            // 用户条件筛选
            if (list.size() > 0) {
                for (PlatformAddress address : list) {
                    Integer addressId = address.getId();
                    orderLambdaQueryWrapper.eq(SaleOrder::getAddressId, addressId);
                }
            }
        }
        // 状态条件筛选
        orderLambdaQueryWrapper.eq(Strings.isNotEmpty(status), SaleOrder::getStatus, status);
        // 状态条件判断
        if (code == 0) {
            orderLambdaQueryWrapper.between(SaleOrder::getStatus, -1, 2);
        } else {
            orderLambdaQueryWrapper.between(SaleOrder::getStatus, 3, 5);
        }
        orderService.page(orderPage, orderLambdaQueryWrapper);

        // 整合
        Page<OrderVo> orderVoPage = new Page<>();
        BeanUtils.copyProperties(orderPage, orderVoPage, "records");
        List<SaleOrder> records = orderPage.getRecords();
        List<OrderVo> list = records.stream().map((item) -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(item, orderVo);
            // 获取用户id
            Integer userId1 = item.getUserId();
            // 根据用户id查询用户
            PlatformUser userById = userService.getById(userId1);
            // 保存用户
            orderVo.setUser(userById);

            // 获取地址id
            Integer addressId = item.getAddressId();
            // 根据地址id查询地址
            PlatformAddress addressById = addressService.getById(addressId);
            // 保存地址
            orderVo.setAddress(addressById);

            return orderVo;
        }).collect(Collectors.toList());

        orderVoPage.setRecords(list);
        return R.success(orderVoPage);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("根据订单id获取订单详情")
    public R getDetail(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<SaleOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleOrderDetail::getOrderId, id);
        List<SaleOrderDetail> list = orderDetailService.list(queryWrapper);
        return R.success(list);
    }

    @PutMapping("/{id}/{status}")
    @ApiOperation("修改订单状态")
    public R editStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        SaleOrder order = orderService.getById(id);
        order.setStatus(status);
        orderService.updateById(order);
        return R.success(null);
    }

    @PostMapping
    @ApiOperation("添加订单")
    public R save(@RequestBody OrderDto orderDto) {
        // 获取订单信息保存
        SaleOrder order = new SaleOrder();
        order.setUserId(orderDto.getUserId());
        order.setAddressId(orderDto.getAddressId());
        order.setStatus(orderDto.getStatus());
        order.setNumber(String.valueOf(UUID.randomUUID()));
        order.setAmount(orderDto.getAmount());
        orderService.save(order);

        // 获取订单id
        Integer orderId = order.getId();

        // 获取订单详情信息
        List<SaleOrderDetail> orderDetails = orderDto.getOrderDetails();
        for (SaleOrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(orderId);
            orderDetailService.save(orderDetail);
        }

        // 清空购物车
        LambdaQueryWrapper<SaleShopcart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SaleShopcart::getUserId, orderDto.getUserId());
        shopcartService.remove(queryWrapper);

        return R.success(orderId);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("取消订单")
    public R remove(@PathVariable("id") Integer id) {
        // 删除订单
        orderService.removeById(id);
        // 删除订单详情
        orderDetailService.remove(new LambdaQueryWrapper<SaleOrderDetail>().eq(
                SaleOrderDetail::getOrderId, id
        ));
        return R.success(null);
    }

    @GetMapping("/{userId}")
    @ApiOperation("根据用户id获取订单")
    public R getByUserId(@PathVariable("userId") Integer userId, SaleOrder order) {
        Integer status = order.getStatus();
        Integer id = order.getId();
        // 查询订单
        LambdaQueryWrapper<SaleOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(SaleOrder::getUserId, userId);
        orderLambdaQueryWrapper.eq(status != null, SaleOrder::getStatus, status);
        orderLambdaQueryWrapper.eq(id != null, SaleOrder::getId, id);
        List<SaleOrder> orderList = orderService.list(orderLambdaQueryWrapper);

        List<OrderVo> orderVoList = orderList.stream().map(item -> {
            OrderVo orderVo = new OrderVo();
            // 订单信息
            BeanUtils.copyProperties(item, orderVo);
            // 查询订单详情
            Integer orderId = item.getId();
            List<SaleOrderDetail> orderDetailList = orderDetailService.list(new LambdaQueryWrapper<SaleOrderDetail>().eq(SaleOrderDetail::getOrderId, orderId));
            orderVo.setOrderDetails(orderDetailList);
            // 查询用户
            Integer uId = item.getUserId();
            PlatformUser user = userService.getById(uId);
            orderVo.setUser(user);
            // 查询地址
            Integer addressId = item.getAddressId();
            PlatformAddress address = addressService.getById(addressId);
            orderVo.setAddress(address);
            return orderVo;
        }).collect(Collectors.toList());
        return R.success(orderVoList);
    }

    @GetMapping("/count/{userId}")
    @ApiOperation("根据用户id获取订单数量")
    public R getCountByUserId(@PathVariable("userId") Integer userId) {
        OrderCountVo countVo = new OrderCountVo();
        // 待付款订单数量
        countVo.setMinusOneCount(orderService.count(new LambdaQueryWrapper<SaleOrder>()
                .eq(SaleOrder::getStatus, -1)
                .eq(userId != 0, SaleOrder::getUserId, userId)
        ));
        // 待发货订单数量
        countVo.setZeroCount(orderService.count(new LambdaQueryWrapper<SaleOrder>()
                .eq(SaleOrder::getStatus, 0)
                .eq(userId != 0, SaleOrder::getUserId, userId)
        ));
        // 待收货订单数量
        countVo.setOneCount(orderService.count(new LambdaQueryWrapper<SaleOrder>()
                .eq(SaleOrder::getStatus, 1)
                .eq(userId != 0, SaleOrder::getUserId, userId)
        ));
        // 待发货订单数量
        countVo.setThreeCount(orderService.count(new LambdaQueryWrapper<SaleOrder>()
                .eq(SaleOrder::getStatus, 3)
                .eq(userId != 0, SaleOrder::getUserId, userId)
        ));
        // 收藏数量
        countVo.setCollectCount(collectService.count(new LambdaQueryWrapper<ProductCollect>()
                .eq(userId != 0, ProductCollect::getUserId, userId)
        ));
        // 浏览数量
        countVo.setHistoryCount(historyService.count(new LambdaQueryWrapper<ProductHistory>()
                .eq(userId != 0, ProductHistory::getUserId, userId)
        ));

        return R.success(countVo);
    }
}
