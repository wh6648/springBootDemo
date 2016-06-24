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

import com.rijia.workPlatform.entity.PositionEntity;
import com.rijia.workPlatform.req.FormReq;
import com.rijia.workPlatform.service.PositionService;
import com.rijia.workPlatform.util.Common;

@Controller
@RequestMapping("positionInfo")
public class PositionController {
	private static Logger logger = LoggerFactory.getLogger(PositionController.class);

	@Autowired
	private PositionService positionService;

	@RequestMapping(value = { "/getAllPositionInfo" })
	@ResponseBody
	public FormReq getAllPositionInfo(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		logger.debug("getAllPositionInfo");

		FormReq formReq = new FormReq();

		List<PositionEntity> positionList = positionService.getAllPositionInfo();
		formReq.getRetMap().put("allPosition", positionList);

		return formReq;
	}

	@RequestMapping(value = { "/savePositionInfo" })
	@ResponseBody
	public FormReq savePositionInfo(@RequestBody LinkedHashMap<String, String> dataReqMap) {
		logger.debug("savePositionInfo");

		FormReq formReq = new FormReq();
		formReq.setError(true);

		String name = Common.getValueFromBean(dataReqMap.get("name"));

		formReq.setMsg("注册失败!");
		PositionEntity positionEntity = null;
		if (!StringUtils.isEmpty(name)) {
			positionEntity = positionService.findByUserName(name);
			if (positionEntity == null) {
				positionEntity = new PositionEntity();
				positionEntity.setName(name);

				positionService.save(positionEntity);

				formReq.setError(false);
				formReq.setMsg(StringUtils.EMPTY);
			} else {
				formReq.setMsg("指定的职位已被注册!");
			}
		}

		return formReq;
	}
}