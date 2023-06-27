package com.zgq.medicine.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.PlatformUser;
import com.zgq.medicine.service.PlatformUserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zgq
 * Create: 2023/5/15 22:31
 * Description:
 */

@RestController
@RequestMapping("/user")
@Api(tags = "商城用户模块")
public class PlatformUserController {
    @Autowired
    private PlatformUserService userService;

    // 登录
    @GetMapping("/login")
    @ApiOperation("商城用户登录")
    public R login(String username, String password) {
        // 查询用户是否存在
        LambdaQueryWrapper<PlatformUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformUser::getUsername, username);
        PlatformUser user = userService.getOne(queryWrapper);

        if (user == null) {
            return R.error("用户不存在");
        }

        // 验证密码是否正确
        if (!user.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        // 判断状态
        if (user.getStatus() != 0) {
            return R.error("用户已被禁用");
        }

        // jwt生成token
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> map = new HashMap<>();
        String token = builder.setSubject(username) // token携带的数据
                .setIssuedAt(new Date()) // token生成时间
                .setId(user.getId() + "") // 设置用户id为token的唯一标识
                .setClaims(map) // map中可以存放用户的角色权限信息
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 3)) // token过期时间
                .signWith(SignatureAlgorithm.HS256, "medicine_sale_token") // 设置加密方式和加密密码
                .compact();

        return new R(0, token, user, null);
    }
}
