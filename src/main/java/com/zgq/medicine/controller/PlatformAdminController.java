package com.zgq.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zgq.medicine.common.R;
import com.zgq.medicine.entity.PlatformAdmin;
import com.zgq.medicine.service.PlatformAdminService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 平台管理员表 (PlatformAdmin)表控制层
 *
 * @author makejava
 * @since 2023-05-11 09:28:19
 */
@RestController
@RequestMapping("platformAdmin")
@CrossOrigin
@Api(tags = "平台管理员")
public class PlatformAdminController {
    /**
     * 服务对象
     */
    @Resource
    private PlatformAdminService platformAdminService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询所有管理员
     *
     * @param page
     * @param pageSize
     * @param username
     * @return
     */
    @GetMapping
    @ApiOperation("管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", defaultValue = "5"),
            @ApiImplicitParam(name = "username", value = "账号")
    })
    public R selectAll(Integer page, Integer pageSize, String username) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        // 分页
        Page<PlatformAdmin> pageInfo = new Page<>(page, pageSize);
        // 条件
        LambdaQueryWrapper<PlatformAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(username), PlatformAdmin::getUsername, username);
        queryWrapper.orderByAsc(PlatformAdmin::getId);
        // 执行
        platformAdminService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取信息")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(platformAdminService.getById(id));
    }

    /**
     * 添加管理员
     *
     * @param platformAdmin 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("添加管理员")
    public R insert(@RequestBody PlatformAdmin platformAdmin) {
        // 查询用户
        LambdaQueryWrapper<PlatformAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformAdmin::getUsername, platformAdmin.getUsername());
        PlatformAdmin admin = platformAdminService.getOne(queryWrapper);

        // 判断用户是否存在
        if (admin != null) {
            return R.error("用户已存在");
        } else {
            // 设置用户信息
            platformAdmin.setPassword("123456");
            platformAdmin.setPassword(DigestUtils.md5DigestAsHex(platformAdmin.getPassword().getBytes()));
            if (platformAdmin.getAvatar() == null) {
                platformAdmin.setAvatar("default.jpg");
            }
            // 保存用户信息
            return R.success(platformAdminService.save(platformAdmin));
        }
    }

    /**
     * 修改管理员信息
     *
     * @param
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改管理员信息")
    public R update(@RequestParam("id") Integer id, @RequestParam("nickname") String nickname, @RequestParam("avatar") String avatar) {
        PlatformAdmin admin = platformAdminService.getById(id);
        admin.setNickname(nickname);
        admin.setAvatar(avatar);
        return R.success(platformAdminService.updateById(admin));
    }

    /**
     * 修改管理员密码
     *
     * @param id
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PutMapping("/pwd")
    @ApiOperation("修改管理员密码")
    @CrossOrigin
    public R updatePwd(Integer id, String oldPwd, String newPwd) {
        PlatformAdmin admin = platformAdminService.getById(id);
        if (oldPwd.equals(newPwd)) {
            return R.error("新旧密码一致");
        }

        if (!admin.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))) {
            return R.error("旧密码不正确");
        }
        admin.setPassword(DigestUtils.md5DigestAsHex(newPwd.getBytes()));
        return R.success(platformAdminService.updateById(admin));
    }

    /**
     * 删除数据
     *
     * @param id 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除管理员")
    @ApiImplicitParam(name = "id", value = "管理员id", required = true)
    public R delete(@RequestParam("id") Integer id) {
        PlatformAdmin admin = platformAdminService.getById(id);
        admin.setStatus(1);
        return R.success(platformAdminService.updateById(admin));
    }

    /**
     * 登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @GetMapping("/login")
    @ApiOperation("管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    public R login(String username, String password, String vercode) {
        if (username.length() > 8 || username.length() <= 3) {
            return R.error("账号长度应该在3-8位");
        }
        if (password.length() > 16 || password.length() < 6) {
            return R.error("密码长度应该在6-16位");
        }

        // 从redis中获取验证码
        if (!"".equals(vercode)) {
            String verCode = (String) redisTemplate.opsForValue().get("verCode");
            if (!verCode.equals(vercode)) {
                return R.error("验证码错误");
            }
        }


        // 根据用户名查询用户
        LambdaQueryWrapper<PlatformAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformAdmin::getUsername, username);
        PlatformAdmin admin = platformAdminService.getOne(queryWrapper);

        // 判断用户是否存在
        if (admin == null) {
            return R.error("用户不存在");
        }

        // 密码加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 判断密码是否正确
        if (!admin.getPassword().equals(password)) {
            return R.error("密码不正确");
        }

        // 判断用户状态
        if (admin.getStatus() != 0) {
            return R.error("用户已被禁用");
        }

        // jwt生成token
        JwtBuilder builder = Jwts.builder();
        Map<String, Object> map = new HashMap<>();
        String token = builder.setSubject(username) // token携带的数据
                .setIssuedAt(new Date()) // token生成时间
                .setId(admin.getId() + "") // 设置用户id为token的唯一标识
                .setClaims(map) // map中可以存放用户的角色权限信息
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 3)) // token过期时间
                .signWith(SignatureAlgorithm.HS256, "medicine_sale_token") // 设置加密方式和加密密码
                .compact();

        // 登录成功，清除redis缓存的验证码
        redisTemplate.delete("verCode");

        return new R(0, token, admin, null);
    }

    // 注册
    @PostMapping("/register")
    @ApiOperation("管理员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public R register(String username, String password) {
        // 查询用户
        LambdaQueryWrapper<PlatformAdmin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformAdmin::getUsername, username);
        PlatformAdmin admin = platformAdminService.getOne(queryWrapper);

        // 判断用户是否存在
        if (admin != null) {
            return R.error("用户已存在");
        } else {
            // 设置用户信息
            PlatformAdmin platformAdmin = new PlatformAdmin();
            platformAdmin.setUsername(username);
            platformAdmin.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            platformAdmin.setAvatar("default.jpg");
            platformAdmin.setCreateTime(LocalDateTime.now());
            platformAdmin.setUpdateTime(LocalDateTime.now());

            // 保存用户信息
            platformAdminService.save(platformAdmin);
            return R.success(null);
        }
    }

}

