package com.gszcn.security.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午4:05
 */
@Getter
@Setter
public class CaptchaException extends AuthenticationException {
    private Integer code=402;
    public CaptchaException(String msg) {
        super(msg);
    }
    public CaptchaException(Integer code, String msg) {
        super(msg);
        this.code=code;
    }
}
