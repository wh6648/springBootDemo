package com.rijia.workPlatform.controllers;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rijia.workPlatform.entity.DefectEntity;
import com.rijia.workPlatform.req.FormReq;
import com.rijia.workPlatform.service.DefectService;
import com.rijia.workPlatform.util.Common;

@Controller
@RequestMapping("defect")
public class DefectController {
	private static Logger logger = LoggerFactory.getLogger(DefectController.class);

	@Autowired
	private DefectService defectService;

	@RequestMapping("/getAllDefect")
	@ResponseBody
	public FormReq getAllDefect(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		logger.debug("getAllDefect");
		FormReq formReq = new FormReq();

		List<DefectEntity> defectEntityList = defectService.getAllDefect();

		formReq.getRetMap().put("allDefect", defectEntityList);
		return formReq;
	}

	@RequestMapping("/saveDefect")
	@ResponseBody
	public FormReq saveDefect(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		logger.debug("saveDefect");

		FormReq formReq = new FormReq();
		formReq.setError(true);

		String type = Common.getValueFromBean(dataReqMap.get("type"));
		String name = Common.getValueFromBean(dataReqMap.get("name"));

		formReq.setMsg("保存失败!");
		DefectEntity defectEntity = null;
		if (!StringUtils.isEmpty(name)) {
			defectEntity = defectService.findByUserName(name);
			if (defectEntity == null) {
				defectEntity = new DefectEntity();
				defectEntity.setType(type);
				defectEntity.setName(name);

				defectService.save(defectEntity);

				formReq.setError(false);
				formReq.setMsg(StringUtils.EMPTY);
			} else {
				formReq.setMsg("指定的缺陷已存在!");
			}
		}

		return formReq;

	}
}
