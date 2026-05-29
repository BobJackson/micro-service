package com.wangyousong.practice.gateway.filter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 鉴权过滤器配置
 * 统一在 Gateway 层进行鉴权，避免每个微服务重复实现
 */
@Configuration
public class SaTokenAuthFilter {

    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截所有路由
                .addInclude("/**")
                // 放行路径：认证相关接口不需要鉴权
                .addExclude(
                        "/auth/login",
                        "/auth/register",
                        "/auth/logout",
                        // Spring Boot Admin 监控所需的 Actuator 端点
                        "/actuator/**"
                )
                // 鉴权逻辑
                .setAuth(obj -> {
                    // 校验 token 是否有效，无效则抛出异常
                    StpUtil.checkLogin();
                })
                // 异常处理
                .setError(e -> {
                    SaHolder.getResponse().setStatus(401);
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    return SaResult.error("未登录或token已过期").setCode(401);
                });
    }
}
