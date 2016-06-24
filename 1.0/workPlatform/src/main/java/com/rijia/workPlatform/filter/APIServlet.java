package com.rijia.workPlatform.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class APIServlet extends HttpServlet {
	private static final long serialVersionUID = -3015589524713506951L;
	private String[] excludeUrls = {};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("==>DemoServlet doGet");
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("==>DemoServlet doPost");

		// 不允许浏览器端或缓存服务器缓存当前页面信息。
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "no-cache");// 浏览器和缓存服务器都不应该缓存页面信息
		resp.addHeader("Cache-Control", "no-store");// 请求和响应的信息都不应该被存储在对方的磁盘系统中；
		resp.addHeader("Cache-Control", "must-revalidate");// 客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；
	}

	public void setExcludeUrls(final String excludeUrls) {
	}

}
