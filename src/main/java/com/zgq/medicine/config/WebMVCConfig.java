package com.zgq.medicine.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.zgq.medicine.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author: zgq
 * Create: 2023/5/11 8:51
 * Description:
 */

@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
public class WebMVCConfig extends WebMvcConfigurationSupport {

    /**
     * redis工具类
     */
    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

    /**
     * 跨域处理
     */
    @Bean
    public CorsFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1. 配置跨域
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许传递cookie
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


    /**
     * 静态资源映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开启静态资源映射...");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * Knife4j接口文档
     */
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zgq.medicine.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("医药分销平台")
                .version("1.0")
                .description("医药分销平台接口文档")
                .contact(new Contact("zgq", "gq2617@github.io/web", "zgq2617@163.com"))
                .build();
    }
}
