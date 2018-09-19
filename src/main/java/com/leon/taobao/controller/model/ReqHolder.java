package com.leon.taobao.controller.model;

import java.util.Map;

public class ReqHolder {
    private static ThreadLocal<ReqInfo> reqInfoThreadLocal = new ThreadLocal<ReqInfo>();

    public static void setReqInfo(ReqInfo reqInfo) {
        reqInfoThreadLocal.set(reqInfo);
    }

    public static ReqInfo getReqInfo() {
        return reqInfoThreadLocal.get();
    }

    public static String getContextPath() {
        return reqInfoThreadLocal.get().getContextPath();
    }

    public static String getId() {
        return reqInfoThreadLocal.get().getId();
    }

    public static String getIp() {
        return reqInfoThreadLocal.get().getIp();
    }

    public static String getMethod() {
        return reqInfoThreadLocal.get().getMethod();
    }

    public static Map<String,String[]> getParameters() {
        return reqInfoThreadLocal.get().getParameters();
    }

    public static String getPort() {
        return reqInfoThreadLocal.get().getPort();
    }

    public static String getReqPath() {
        return reqInfoThreadLocal.get().getReqPath();
    }

    public static String getSessionId() {
        return reqInfoThreadLocal.get().getSessionId();
    }

}