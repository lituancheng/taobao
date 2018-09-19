package com.leon.taobao.controller.model;

import java.util.Map;

public class ReqInfo {
    public String id;
    public String ip;
    public String port;
    public String sessionId;
    public String contextPath;
    public String reqPath;
    public String method;
    public Map<String,String[]> parameters;

    private ReqInfo(){};

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String ip;
        private String port;
        private String contextPath;
        private String reqPath;
        private String sessionId;
        private String method;
        private Map<String,String[]> parameters;

        public ReqInfo build() {
            ReqInfo reqInfo = new ReqInfo();
            reqInfo.id = this.id;
            reqInfo.ip = this.ip;
            reqInfo.port = this.port;
            reqInfo.contextPath = this.contextPath;
            reqInfo.reqPath = this.reqPath;
            reqInfo.sessionId = this.sessionId;
            reqInfo.method = this.method;
            reqInfo.parameters = this.parameters;
            return reqInfo;
        }

        public Builder setId(String id) {this.id = id; return this;}
        public Builder setIp(String ip) {this.ip = ip; return this;}
        public Builder setPort(String port) {this.port = port; return this;}
        public Builder setContextPath(String contextPath) {this.contextPath = contextPath; return this;}
        public Builder setReqPath(String reqPath) {this.reqPath = reqPath; return this;}
        public Builder setSessionId(String sessionId) {this.sessionId = sessionId; return this;}
        public Builder setMethod(String method) {this.method = method; return this;}
        public Builder setParameters(Map<String,String[]> parameters) {this.parameters = parameters; return this;}
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getReqPath() {
        return reqPath;
    }

    public void setReqPath(String reqPath) {
        this.reqPath = reqPath;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }
}
