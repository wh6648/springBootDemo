package com.rijia.workPlatform.req;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormReq extends BaseReq {
	private boolean isError = false;
	private String msg;
	private Map<String, Object> retMap;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public Map<String, Object> getRetMap() {
		if (retMap == null) {
			retMap = new LinkedHashMap<>();
		}
		return retMap;
	}
}
