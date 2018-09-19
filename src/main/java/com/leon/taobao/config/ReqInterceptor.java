package com.leon.taobao.config;

import com.google.gson.Gson;
import com.leon.taobao.controller.model.ReqHolder;
import com.leon.taobao.controller.model.ReqInfo;
import com.leon.taobao.log.Logger;
import com.leon.taobao.util.ExpiryMap;
import com.leon.taobao.util.ServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lituancheng on 2018/9/13
 */
@Component
public class ReqInterceptor implements HandlerInterceptor {

    @Autowired
    private Gson gson;

    private static ExpiryMap<String, Boolean> cacheMap = new ExpiryMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws NoSuchFieldException, IllegalAccessException {
        Map<String, String[]> parameters = request.getParameterMap();
        String signature = parameters.get("signature")[0];
        String openid = parameters.get("openid")[0];
        String key = signature + openid;
        if(cacheMap.containsKey(key)){
            return false;
        }else {
            cacheMap.put(key, true, 10 * 1000); //拒绝10s内的重复请求
        }
        String reqId = UUID.randomUUID().toString();
        String contextPath = request.getContextPath();
        String sessionId = request.getSession().getId();
        String requestUri = request.getRequestURI();
        String reqPath = requestUri.substring(contextPath.length());
        String method = request.getMethod();
        String reqIp = ServletRequestUtil.getRemoteIp(request);
        String port = ServletRequestUtil.getRemotePort(request);
        ReqInfo reqInfo = ReqInfo.builder()
                .setId(reqId)
                .setIp(reqIp)
                .setPort(port)
                .setSessionId(sessionId)
                .setContextPath(contextPath)
                .setReqPath(reqPath)
                .setMethod(method)
                .setParameters(parameters)
                .build();
        ReqHolder.setReqInfo(reqInfo);

        Logger.reqLog.info("AccountIp: {}, reqInfo:{}", reqIp, gson.toJson(reqInfo));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
    }

}
