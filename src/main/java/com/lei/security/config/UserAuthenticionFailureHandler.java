package com.lei.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lei.security.exception.CaptchaException;
import com.lei.security.until.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午4:50
 */
@Component("userAuthenticionFailureHandler")
@Slf4j
public class UserAuthenticionFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        log.info("登录失败。。。。");

        String type = request.getParameter("type");
        // 根据登录时type字段是JSON，否则就走默认的
        if (!StringUtils.isEmpty(type) && "JSON".equals(type.toUpperCase())) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            // 返回认证时产生的认证异常信息
            R result = R.error(401, authenticationException.getMessage());
            // 如果是验证码异常，返回402及验证码异常信息
            if(authenticationException instanceof CaptchaException){
                CaptchaException e1 = (CaptchaException) authenticationException;
                result=R.error(e1.getCode(), authenticationException.getMessage());
            }
            response.getWriter().write(jacksonObjectMapper.writeValueAsString(result));
        } else {
            super.onAuthenticationFailure(request, response, authenticationException);
        }

    }
}
