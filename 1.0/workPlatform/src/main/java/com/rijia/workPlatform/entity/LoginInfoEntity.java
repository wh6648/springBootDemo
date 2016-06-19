package com.rijia.workPlatform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_login_Info")
public class LoginInfoEntity {

	@Id
	@Column(name = "device_token")
	private String deviceToken;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "login_time")
	private Date loginTime;

	public LoginInfoEntity() {
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
