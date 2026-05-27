package com.wangyousong.practice.employee.config;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 审计信息提供者
 * 从 Sa-Token 中获取当前登录用户作为审计信息
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            // 从 Sa-Token 获取当前登录用户ID
            if (StpUtil.isLogin()) {
                return Optional.of(StpUtil.getLoginIdAsString());
            }
        } catch (Exception e) {
            // 如果未登录或获取失败，返回空
        }
        return Optional.empty();
    }
}
