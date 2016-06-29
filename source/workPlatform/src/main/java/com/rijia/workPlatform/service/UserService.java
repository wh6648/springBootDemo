package com.rijia.workPlatform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijia.workPlatform.entity.UserEntity;
import com.rijia.workPlatform.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public List<UserEntity> findAll() {
		List<UserEntity> userEntityList = new ArrayList<>();
		UserEntity user = null;
		Iterator<UserEntity> it = userDao.findAll().iterator();
		while (it.hasNext()) {
			user = it.next();
			user.setPassword(StringUtils.EMPTY);
			userEntityList.add(user);
		}

		return userEntityList;
	}

	public UserEntity findByUserName(String name) {
		return userDao.findByName(name);
	}

	public UserEntity findByUserNameAndPass(String name, String pass) {
		return userDao.findByNameAndPassword(name, pass);
	}

	public UserEntity save(UserEntity user) {
		return userDao.save(user);
	}
}
