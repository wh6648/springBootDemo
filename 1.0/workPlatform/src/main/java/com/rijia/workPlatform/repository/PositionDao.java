package com.rijia.workPlatform.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.rijia.workPlatform.entity.PositionEntity;

@Transactional
public interface PositionDao extends CrudRepository<PositionEntity, String> {
}
