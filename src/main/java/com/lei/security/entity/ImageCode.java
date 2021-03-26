package com.lei.security.entity;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午3:38
 */
@Data
public class ImageCode {
    /**
     * 验证码
     */
    private String code;
    /**
     * 设置超时时间
     */
    private LocalDateTime expireTime;

    public ImageCode(String code, Integer expireTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public boolean isexpire() {
        Duration between = Duration.between(LocalDateTime.now(), expireTime);
        long l = between.toMillis();
        return l < 0;
    }
}
