package com.rijia.workPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijia.workPlatform.entity.UserEntity;
import com.rijia.workPlatform.repository.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

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
