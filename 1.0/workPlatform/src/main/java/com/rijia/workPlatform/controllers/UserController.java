package com.rijia.workPlatform.controllers;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rijia.workPlatform.entity.LoginInfoEntity;
import com.rijia.workPlatform.entity.UserEntity;
import com.rijia.workPlatform.req.FormReq;
import com.rijia.workPlatform.service.LoginInfoService;
import com.rijia.workPlatform.service.UserService;
import com.rijia.workPlatform.util.Common;
import com.rijia.workPlatform.util.LoginUserUtils;
import com.rijia.workPlatform.util.MD5;

@Controller
@RequestMapping("userInfo")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LoginInfoService loginInfoService;

	@RequestMapping("/currentUser")
	@ResponseBody
	public FormReq currentUser(@RequestBody final LinkedHashMap<String, String> dataReqMap) {
		FormReq forReq = new FormReq();
		String deviceToken = Common.getValueFromBean(dataReqMap.get("deviceToken"));

		forReq.setMsg("没有用户登录!");
		UserEntity user = LoginUserUtils.getAPISessionUser();
		if (user != null) {
			setLoginInfo(forReq, user, deviceToken);
		}

		return forReq;
	}

	@RequestMapping("/login")
	@ResponseBody
	public FormReq login(@RequestBody final LinkedHashMap<String, String> dataReqMap) {
		FormReq forReq = new FormReq();
		String name = Common.getValueFromBean(dataReqMap.get("name"));
		String password = Common.getValueFromBean(dataReqMap.get("password"));
		String deviceToken = Common.getValueFromBean(dataReqMap.get("deviceToken"));

		UserEntity userEntity = userService.findByUserNameAndPass(name, MD5.string2MD5(password));
		if (userEntity != null) {
			LoginInfoEntity userOtherDeviceLoginInfo = loginInfoService.getOtherDeviceLoginInfoByUserId(deviceToken,
					userEntity.getId());
			if (userOtherDeviceLoginInfo != null) {
				forReq.setMsg("已在其他设备上登录! 设备ID : " + userOtherDeviceLoginInfo.getDeviceToken());
			} else {
				LoginInfoEntity deviceLoginInfo = loginInfoService.getLoginInfoByDeviceToken(deviceToken);
				if (deviceLoginInfo == null) {
					deviceLoginInfo = new LoginInfoEntity();
					deviceLoginInfo.setDeviceToken(deviceToken);
				}
				deviceLoginInfo.setUserId(userEntity.getId());
				deviceLoginInfo.setLoginTime(new Date());
				deviceLoginInfo = loginInfoService.save(deviceLoginInfo);

				LoginUserUtils.setAPISessionUser(userEntity);

				setLoginInfo(forReq, userEntity, deviceToken);
			}
		} else {
			forReq.setMsg("用户或密码错误,登录失败!");
		}

		return forReq;
	}

	@RequestMapping("/createUser")
	@ResponseBody
	public FormReq createUser(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		FormReq forReq = new FormReq();
		String name = Common.getValueFromBean(dataReqMap.get("name"));
		String password = Common.getValueFromBean(dataReqMap.get("password"));
		String deviceToken = Common.getValueFromBean(dataReqMap.get("deviceToken"));

		forReq.setMsg("注册失败!");
		UserEntity userEntity = null;
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(password)) {
			userEntity = userService.findByUserName(name);
			if (userEntity == null) {
				userEntity = new UserEntity();
				userEntity.setName(name);
				userEntity.setPassword(MD5.string2MD5(password));
				userEntity = userService.save(userEntity);

				setLoginInfo(forReq, userEntity, deviceToken);
			}
		}

		return forReq;
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(long id) {
		return "{}";
	}

	@RequestMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(long id, String email, String name) {
		return "{}";
	}

	private void setLoginInfo(FormReq forReq, UserEntity userEntity, String deviceToken) {
		forReq.getUser().setUserId(userEntity.getId());
		forReq.getUser().setUserName(userEntity.getName());
		forReq.getUser().setDeviceToken(deviceToken);
		forReq.getUser().setLogin(true);
		forReq.setMsg(StringUtils.EMPTY);
	}
}