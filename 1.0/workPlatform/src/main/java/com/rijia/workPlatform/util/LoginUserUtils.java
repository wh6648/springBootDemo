package com.rijia.workPlatform.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rijia.workPlatform.entity.UserEntity;

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
	public static UserEntity getSessionLoginUser() {

		if (RequestContextHolder.getRequestAttributes() != null) {
			UserEntity UserEntity = (UserEntity) RequestContextUtils.getSessionAttribute(Constants.LOGIN_USER);
			if (UserEntity == null) {
				UserEntity = getAPISessionUser();
			}
			return UserEntity;
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
	public static void setAPISessionUser(UserEntity userEntity) {
		RequestContextUtils.setSessionAttribute(Constants.API_USER, userEntity);
	}

	/*
	 * 
	 * 获取手机端登录用户
	 */
	public static UserEntity getAPISessionUser() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			return (UserEntity) RequestContextUtils.getSessionAttribute(Constants.API_USER);
		} else {
			return null;
		}
	}

}
