package com.leon.taobao.config;

import com.google.gson.Gson;
import com.leon.taobao.aes.AesException;
import com.leon.taobao.aes.WXBizMsgCrypt;
import com.leon.taobao.model.WeChatConstant;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{

    @Bean
    public WXBizMsgCrypt wxBizMsgCrypt() throws AesException {
        return new WXBizMsgCrypt(WeChatConstant.TOKEN, WeChatConstant.AES_KEY, WeChatConstant.appID);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    public TaobaoClient taobaoClient(){
        return new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "24809138", "fe60df0386813b642db343c9f9ee3ec5");
    }
}