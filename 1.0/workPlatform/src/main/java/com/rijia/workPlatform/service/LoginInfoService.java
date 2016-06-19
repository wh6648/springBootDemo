package com.rijia.workPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijia.workPlatform.entity.LoginInfoEntity;
import com.rijia.workPlatform.repository.LoginInfoDao;

@Service
public class LoginInfoService {
	@Autowired
	private LoginInfoDao loginInfoDao;

	public LoginInfoEntity getOtherDeviceLoginInfoByUserId(String deviceToken, String userId) {
		LoginInfoEntity loginInfo = loginInfoDao.findByUserId(userId);
		if (loginInfo != null && !loginInfo.getDeviceToken().equals(deviceToken)) {
			return loginInfo;
		}

		return null;
	}

	public LoginInfoEntity getLoginInfoByDeviceToken(String deviceToken) {
		return loginInfoDao.findOne(deviceToken);
	}

	public LoginInfoEntity save(LoginInfoEntity info) {
		return loginInfoDao.save(info);
	}
}
