package com.rijia.workPlatform.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rijia.workPlatform.req.UserReq;

/**
 * 登录用户工具类。
 * 
 */
public class LoginUserUtils {

	/**
	 * private constructor
	 */
	private LoginUserUtils() {
	}

	/**
	 * 获取登录用户的Session Key
	 * 
	 * @param request
	 *            HTTP请求
	 * @return 登录用户的Session Key
	 */
	public static String getLoginUserSessionKey() {
		return Constants.LOGIN_USER;
	}

	/**
	 * 取Session中的登录用户(不建议在Service层调用)。
	 * 
	 * @return 登录用户
	 */
	public static UserReq getSessionLoginUser() {

		if (RequestContextHolder.getRequestAttributes() != null) {
			UserReq userReq = (UserReq) RequestContextUtils.getSessionAttribute(Constants.LOGIN_USER);
			if (userReq == null) {
				userReq = getAPISessionUser();
			}
			return userReq;
		}
		return getAPISessionUser();
	}

	/**
	 * 清空Session中的登录用户(不建议在Service层调用)。
	 */
	public static void clearSessionLoginUser() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			RequestContextUtils.setSessionAttribute(Constants.LOGIN_USER, null);
		}
	}

	/**
	 * 清空Session中的登录用户［前端APIsession］。
	 */
	public static void clearAPISessionLoginUser() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			RequestContextUtils.setSessionAttribute(Constants.API_USER, null);
		}
	}

	/*
	 * 设置手机端登录用户
	 */
	public static void setAPISessionUser(UserReq userReq) {
		RequestContextUtils.setSessionAttribute(Constants.API_USER, userReq);
	}

	/*
	 * 
	 * 获取手机端登录用户
	 */
	public static UserReq getAPISessionUser() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			return (UserReq) RequestContextUtils.getSessionAttribute(Constants.API_USER);
		} else {
			return null;
		}
	}

}
