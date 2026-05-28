package com.wangyousong.practice.attendance.config;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAwareImpl() {
        return () -> {
            try {
                if (StpUtil.isLogin()) {
                    return Optional.of(StpUtil.getLoginIdAsString());
                }
            } catch (Exception e) {
                // 未登录或获取失败
            }
            return Optional.empty();
        };
    }
}
