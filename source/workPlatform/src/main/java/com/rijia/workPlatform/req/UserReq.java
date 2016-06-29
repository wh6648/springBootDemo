package com.rijia.workPlatform.req;

public class UserReq {
	private String userName;
	private String userId;
	private String deviceToken;
	private boolean isLogin;

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/*
	 * 
	 * final TelephonyManager tm = (TelephonyManager)
	 * getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
	 * 
	 * final String tmDevice, tmSerial, tmPhone, androidId; tmDevice = "" +
	 * tm.getDeviceId(); tmSerial = "" + tm.getSimSerialNumber(); androidId = ""
	 * + android.provider.Settings.Secure.getString(getContentResolver(),
	 * android.provider.Settings.Secure.ANDROID_ID);
	 * 
	 * UUID deviceUuid = new UUID(androidId.hashCode(),
	 * ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode()); String uniqueId
	 * = deviceUuid.toString();
	 * 
	 * 最后的deviceID可能是这样的结果: 00000000-54b3-e7c7-0000-000046bffd97
	 * 
	 */
}
