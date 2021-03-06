package com.leon.taobao.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ServletRequestUtil {

	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();

	}



	public static String getRemotePort(HttpServletRequest request) {
		String remotePort = request.getHeader("X-Real-Port");
		if(null == remotePort)
			remotePort = "" + request.getRemotePort();
		return remotePort;
	}

	public static String getIpPort(HttpServletRequest request){
		return getRemoteIp(request) + ":" + getRemotePort(request);
	}

	public static String getFullURI(HttpServletRequest request){
		String uri = request.getRequestURI();
		String queryStr = request.getQueryString();
		if(!StringUtils.isEmpty(queryStr)){
			uri += "?" + queryStr;
		}
		return uri;
	}
}
