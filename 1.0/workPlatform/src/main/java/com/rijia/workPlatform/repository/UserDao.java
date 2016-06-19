package com.rijia.workPlatform.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.rijia.workPlatform.entity.UserEntity;

@Transactional
public interface UserDao extends CrudRepository<UserEntity, Long> {

	public UserEntity findByName(String name);

	public UserEntity findByNameAndPassword(String name, String pass);
}
