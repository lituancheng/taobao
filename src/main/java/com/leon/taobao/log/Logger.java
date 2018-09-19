package com.leon.taobao.log;

import org.apache.logging.log4j.LogManager;

/**
 * Created by lituancheng on 2018/9/13
 */
public class Logger {

    //请求日志 应当仅由ReqInterceptor输出
    public static org.apache.logging.log4j.Logger reqLog = LogManager.getLogger("reqLogger");
    //服务状态日志
    public static org.apache.logging.log4j.Logger statusLog = LogManager.getLogger("statusLogger");

    public static org.apache.logging.log4j.Logger errorLog = LogManager.getLogger("errorLogger");
    public static org.apache.logging.log4j.Logger infoLog = LogManager.getLogger("infoLogger");
    public static org.apache.logging.log4j.Logger debugLog = LogManager.getLogger("debugLogger");

    //root日志，所有代码中未定义的Logger会打到这里
    //例如    LogManager.getLogger("unknownLogger");
    //       LogManager.getLogger(this.getClass().getName());
    public static org.apache.logging.log4j.Logger messLog = LogManager.getLogger("messLogger");
}
