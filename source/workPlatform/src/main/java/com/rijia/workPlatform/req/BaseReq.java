package com.rijia.workPlatform.req;

public class BaseReq {
	private UserReq user;

	public UserReq getUser() {
		if (user == null) {
			user = new UserReq();
		}
		return user;
	}

	public void setUser(UserReq userReq) {
		this.user = userReq;
	}
}
