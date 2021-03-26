package com.lei.security.filter;

import com.lei.security.config.UserAuthenticionFailureHandler;
import com.lei.security.entity.ImageCode;
import com.lei.security.exception.CaptchaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午4:04
 */
@Component
@Slf4j
public class ValidateFilter extends OncePerRequestFilter {
    @Autowired
    private UserAuthenticionFailureHandler userAuthenticionFailureHandler;
    /**
     * 验证路径正则匹配
     */
    private static final PathMatcher pathMatcher = new AntPathMatcher();


    /**
     * 拦截器
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if ("POST".equals(request.getMethod()) && isProtectedUrl(request)) {

            try {
                validata(request);
            } catch (CaptchaException e) {
//                e.printStackTrace();
                userAuthenticionFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行通过
        filterChain.doFilter(request, response);
    }

    private void validata(HttpServletRequest request) {

        ImageCode attribute = (ImageCode) request.getSession().getAttribute("imagecodekey");
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            throw new CaptchaException(402, "验证码不能为空");
        } else if (attribute == null) {
            throw new CaptchaException(402, "验证码不存在");
        } else if (attribute.isexpire()) {
            throw new CaptchaException(402, "验证已码过期");
        } else if (!code.toLowerCase().equals(attribute.getCode().toLowerCase())) {
            throw new CaptchaException(402, "验证码不正确");
        }
    }


    /**
     * 验证token请求 url
     *
     * @param request
     * @return
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        boolean match = false;
        String protectUrlPattern = "/users/login";

        match = pathMatcher.match(protectUrlPattern, request.getServletPath());
        if (match) {
            match = true;
        }

        return match;
    }
}
