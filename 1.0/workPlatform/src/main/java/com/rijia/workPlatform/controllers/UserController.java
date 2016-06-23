package com.rijia.workPlatform.controllers;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rijia.workPlatform.entity.LoginInfoEntity;
import com.rijia.workPlatform.entity.UserEntity;
import com.rijia.workPlatform.req.FormReq;
import com.rijia.workPlatform.req.UserReq;
import com.rijia.workPlatform.service.LoginInfoService;
import com.rijia.workPlatform.service.UserService;
import com.rijia.workPlatform.util.Common;
import com.rijia.workPlatform.util.DateUtils;
import com.rijia.workPlatform.util.LoginUserUtils;
import com.rijia.workPlatform.util.MD5;

@Controller
@RequestMapping("userInfo")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private LoginInfoService loginInfoService;

	@RequestMapping(value = { "/currentUser" })
	@ResponseBody
	public FormReq currentUser() {
		FormReq formReq = new FormReq();
		formReq.setMsg("没有用户登录!");
		formReq.setError(true);

		UserReq userReq = getCurrentUser();
		if (userReq != null) {
			setLoginInfo(formReq, userReq.getUserId(), userReq.getUserName(), userReq.getDeviceToken());
		}

		return formReq;
	}

	@RequestMapping(value = { "/login" })
	@ResponseBody
	public FormReq login(@RequestBody final LinkedHashMap<String, String> dataReqMap) {
		logger.debug("login");

		FormReq formReq = new FormReq();
		formReq.setError(true);

		String name = Common.getValueFromBean(dataReqMap.get("name"));
		String password = Common.getValueFromBean(dataReqMap.get("password"));
		String deviceToken = Common.getValueFromBean(dataReqMap.get("deviceToken"));

		UserEntity userEntity = userService.findByUserNameAndPass(name, MD5.string2MD5(password));
		if (userEntity != null) {
			LoginInfoEntity userOtherDeviceLoginInfo = loginInfoService.getOtherDeviceLoginInfoByUserId(deviceToken,
					userEntity.getId());
			boolean isDoLogin = false;
			if (userOtherDeviceLoginInfo != null) {
				if (DateUtils.isOverEndTime(userOtherDeviceLoginInfo.getLoginTime())) {
					isDoLogin = true;
					loginInfoService.delete(userOtherDeviceLoginInfo);
				} else {
					formReq.setMsg("已在其他设备上登录! 设备ID : " + userOtherDeviceLoginInfo.getDeviceToken());
				}
			} else {
				isDoLogin = true;
			}

			if (isDoLogin) {
				LoginInfoEntity deviceLoginInfo = loginInfoService.getLoginInfoByDeviceToken(deviceToken);
				if (deviceLoginInfo == null) {
					deviceLoginInfo = new LoginInfoEntity();
					deviceLoginInfo.setDeviceToken(deviceToken);
				}
				deviceLoginInfo.setUserId(userEntity.getId());
				deviceLoginInfo.setLoginTime(new Date());
				loginInfoService.save(deviceLoginInfo);

				setLoginInfo(formReq, userEntity.getId(), name, deviceToken);

				LoginUserUtils.setAPISessionUser(formReq.getUser());
			}

		} else {
			formReq.setMsg("用户或密码错误,登录失败!");
		}

		return formReq;
	}

	@RequestMapping(value = { "/createUser" })
	@ResponseBody
	public FormReq createUser(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		FormReq formReq = new FormReq();
		formReq.setError(true);

		String name = Common.getValueFromBean(dataReqMap.get("name"));
		String password = Common.getValueFromBean(dataReqMap.get("password"));
		String deviceToken = Common.getValueFromBean(dataReqMap.get("deviceToken"));

		formReq.setMsg("注册失败!");
		UserEntity userEntity = null;
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(password)) {
			userEntity = userService.findByUserName(name);
			if (userEntity == null) {
				userEntity = new UserEntity();
				userEntity.setName(name);
				userEntity.setPassword(MD5.string2MD5(password));
				userEntity = userService.save(userEntity);

				setLoginInfo(formReq, userEntity.getId(), userEntity.getName(), deviceToken);
			} else {
				formReq.setMsg("指定的用户名已被注册!");
			}
		}

		return formReq;
	}

	private void setLoginInfo(FormReq formReq, String userId, String userName, String deviceToken) {
		UserReq userReq = new UserReq();
		userReq.setUserId(userId);
		userReq.setUserName(userName);
		userReq.setDeviceToken(deviceToken);
		userReq.setLogin(true);

		formReq.setUser(userReq);
		formReq.setError(false);
		formReq.setMsg(StringUtils.EMPTY);
	}

	private UserReq getCurrentUser() {
		UserReq userReq = LoginUserUtils.getAPISessionUser();
		if (userReq != null) {
			LoginInfoEntity deviceLoginInfo = loginInfoService.getLoginInfoByDeviceToken(userReq.getDeviceToken());
			if (DateUtils.isOverEndTime(deviceLoginInfo.getLoginTime())) {
				loginInfoService.delete(deviceLoginInfo);
				LoginUserUtils.clearAPISessionLoginUser();
				userReq = null;
			}
		}

		return userReq;
	}
}