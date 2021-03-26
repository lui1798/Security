package com.lei.security.controller;

import com.lei.security.entity.ImageCode;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @author leiyunlong
 * @version 1.0
 * @since 2020/12/9 下午3:38
 */
@Controller
@RequestMapping("/public/verification")
public class ImageController {
    /**
     * 验证码生成
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/getCode")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImageCode imageCode = this.imageCode(request, response);
        request.getSession().setAttribute("imagecodekey",imageCode );
    }

    /**
     * 图形验证码生成器
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ImageCode imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException, FontFormatException {
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);

        // 三个参数分别为宽、高、位数
        SpecCaptcha gifCaptcha = new SpecCaptcha(130, 48, 4);

        // 设置字体
        // 有默认字体，可以不用设置
        gifCaptcha.setFont(Captcha.FONT_4);

        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        /**
         * 验证码放入缓存
         */
        String text = gifCaptcha.text().toLowerCase();
        // 在getOutputStream之前创建session，不然找不到Session ID，报错
        request.getSession();
        // 输出图片流
        gifCaptcha.out(response.getOutputStream());


        return new ImageCode(gifCaptcha.text().toLowerCase(),120);
    }
}
