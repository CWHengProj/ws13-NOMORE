package com.ws13Fresh.ws13.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.ws13Fresh.ws13.Util.Util;


@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")//String- host to connect to
    private String redisHost;
    @Value("${spring.data.redis.port}")//Integer- port to connect to
    private Integer redisPort;
    @Value("${spring.data.redis.username}")// username - TODO validation to ensure not blank sent
    private String redisUserName;
    @Value("${spring.data.redis.password}")
    private String redisPassword;
    

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName(redisHost);
        rsc.setPort(redisPort);

        if(redisUserName.trim().length() > 0) {
            rsc.setUsername(redisUserName);
            rsc.setPassword(redisPassword);
        }

        JedisClientConfiguration jcc = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jcf = new JedisConnectionFactory(rsc, jcc);
        jcf.afterPropertiesSet();

        return jcf;
    }   

    @Bean(Util.template01)
    public RedisTemplate<String, Object> redisObjectTemplate02() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        

        return template;
    }


}
