package com.gszcn.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszcn.security.until.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *认证成功处理器
 */
@Component("userAuthenticionSuccessHandler")
@Slf4j
public class UserAuthenticionSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登录成功。。。。");
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        // 获取上次访问的地址
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        String targetUrl;
        // 上次访问路径是不是空
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
            //上次访问路径是退出登录
            if(targetUrl.indexOf("logout")!=-1){
                targetUrl = "/index.html";
            }
        } else {
            targetUrl = "/index.html";
        }
        // 根据登录时type字段是JSON，给前端返回浏览器上次访问路径，否则就是走默认的
        String type = request.getParameter("type");
        if (!StringUtils.isEmpty(type) && "JSON".equals(type.toUpperCase())) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            Map <String, Object> data = new HashMap <String, Object>(0);
            data.put("accsee", authentication);
            data.put("targetUrl", targetUrl);
            response.getWriter().write(jacksonObjectMapper.writeValueAsString(R.ok("登陆成功").put(data)));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
