package com.jk.document.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//    RedisTemplate<String, Object> template = new RedisTemplate<>();
//    template.setConnectionFactory(connectionFactory);
//    template.setKeySerializer(new StringRedisSerializer());
//    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//    return template;
//  }
}