package com.rijia.workPlatform.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.rijia.workPlatform.entity.LoginInfoEntity;

@Transactional
public interface LoginInfoDao extends CrudRepository<LoginInfoEntity, String> {
	public LoginInfoEntity findByUserId(String userId);
}
