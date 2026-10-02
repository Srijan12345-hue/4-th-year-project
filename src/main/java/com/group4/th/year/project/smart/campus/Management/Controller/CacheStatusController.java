package com.group4.th.year.project.smart.campus.Management.Controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheStatusController {

    private final StringRedisTemplate redisTemplate;

    public CacheStatusController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/ping")
    public String ping() {
        String response = redisTemplate.getConnectionFactory()
                .getConnection()
                .ping();
        return "Redis status: " + response;
    }
}

