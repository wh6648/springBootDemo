package com.rijia.workPlatform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijia.workPlatform.entity.PositionEntity;
import com.rijia.workPlatform.repository.PositionDao;

@Service
public class PositionService {
	@Autowired
	private PositionDao positionDao;

	public List<PositionEntity> getAllPositionInfo() {
		List<PositionEntity> positionList = new ArrayList<>();

		Iterator<PositionEntity> it = positionDao.findAll().iterator();
		while (it.hasNext()) {
			positionList.add(it.next());
		}

		return positionList;
	}

	public PositionEntity save(PositionEntity position) {
		return positionDao.save(position);
	}

	public PositionEntity findByUserName(String name) {
		return positionDao.findByName(name);
	}
}
