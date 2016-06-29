package com.rijia.workPlatform.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.rijia.workPlatform.entity.DefectEntity;

@Transactional
public interface DefectDao extends CrudRepository<DefectEntity, String> {
	public DefectEntity findByName(String name);
}
