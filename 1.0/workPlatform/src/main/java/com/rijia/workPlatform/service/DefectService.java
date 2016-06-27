package com.rijia.workPlatform.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rijia.workPlatform.entity.DefectEntity;
import com.rijia.workPlatform.repository.DefectDao;

@Service
public class DefectService {
	@Autowired
	private DefectDao defectDao;

	public List<DefectEntity> getAllDefect() {
		List<DefectEntity> defectList = new ArrayList<>();

		Iterator<DefectEntity> it = defectDao.findAll().iterator();
		while (it.hasNext()) {
			defectList.add(it.next());
		}

		return defectList;
	}

	public DefectEntity findByUserName(String name) {
		return defectDao.findByName(name);
	}

	public DefectEntity save(DefectEntity defect) {
		return defectDao.save(defect);
	}
}
