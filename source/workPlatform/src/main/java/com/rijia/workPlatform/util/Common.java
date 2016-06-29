package com.rijia.workPlatform.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Common {

	public static String getValueFromBean(String input) {
		return getValueFromBean(input, StringUtils.EMPTY);
	}

	public static Integer getValueFromBean(Integer input) {
		return getValueFromBean(input, 0);
	}

	public static String getValueFromBean(String input, String defValue) {
		if (input == null || input.trim().isEmpty()) {
			return defValue;
		}

		return input.trim();
	}

	public static Integer getValueFromBean(Integer input, Integer defValue) {
		if (input == null) {
			return defValue;
		}

		return input;
	}

	/**
	 * 去除String数组中的数据前后空格
	 * 
	 * @param arrayStr
	 *            String数组
	 * @return
	 */
	public static List<String> trimArrayStrToList(String[] arrayStr) {
		List<String> strList = new ArrayList<>();

		for (String str : arrayStr) {
			strList.add(str.trim());
		}

		return strList;
	}

	/**
	 * 随机生成自定义属性ID（格式：四位字母+四位数字）
	 * 
	 * @return
	 */
	public static String randomAttrId() {
		return (UUID.randomUpperUUID(4) + UUID.randomNumberUUID(4));
	}

	public static boolean isNullOrEmpty(String input) {
		return (input == null || input.trim().isEmpty());
	}
}
