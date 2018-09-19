package com.leon.taobao.controller.api;

import com.leon.taobao.service.WeChatService;
import com.leon.taobao.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by lituancheng on 2018/9/13
 */
@RestController
@RequestMapping("wechat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     *
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "")
    public String validate(@RequestParam(value = "signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) throws Exception {

        if (WeChatUtil.verifyUrl(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping(value = "")
    public String processMsg(HttpServletRequest request,
                             @RequestParam(value = "openid") String openId) throws UnsupportedEncodingException {
        // 调用核心服务类接收处理请求
        String message = weChatService.processRequest(request, openId);
        return new String(message.getBytes(), "ISO-8859-1");
    }
}
