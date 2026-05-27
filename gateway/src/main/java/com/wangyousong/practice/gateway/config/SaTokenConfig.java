package com.wangyousong.practice.gateway.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Sa-Token 配置类
 */
@Configuration
public class SaTokenConfig {

    /**
     * 使用 Redis 存储 Token
     */
    @Bean
    public SaTokenDao saTokenDao(RedisConnectionFactory redisConnectionFactory) {
        return new SaTokenDaoRedisJackson();
    }
}
