package com.rijia.workPlatform.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rijia.workPlatform.controllers.UserController;
import com.rijia.workPlatform.req.FormReq;
import com.rijia.workPlatform.req.UserReq;

import net.sf.json.JSONObject;

public class APIFilter extends OncePerRequestFilter {

	private String[] excludeUrls = { "/", "/test", "/login", "/currentUser", "/createUser", "/getAllPositionInfo",
			"/getAllSuperiorUserInfo" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 不允许浏览器端或缓存服务器缓存当前页面信息。
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");// 浏览器和缓存服务器都不应该缓存页面信息
		response.addHeader("Cache-Control", "no-store");// 请求和响应的信息都不应该被存储在对方的磁盘系统中；
		response.addHeader("Cache-Control", "must-revalidate");// 客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；

		// java 获取请求 URL
		String url_ = request.getScheme() + "://"; // 请求协议 http 或 https
		url_ += request.getHeader("host"); // 请求服务器
		url_ += request.getRequestURI(); // 工程名
		// 判断请求参数是否为空
		if (request.getQueryString() != null) {
			url_ += "?" + request.getQueryString(); // 参数
		}

		String url = request.getRequestURI().toString();

		if (url.contains(".js") || url.contains(".css") || url.contains(".ico") || url.contains(".map")
				|| url.contains(".ttf") || url.contains(".woff") || url.contains(".eot")) {
			filterChain.doFilter(request, response);
			return;
		}

		for (String eUrl : excludeUrls) {
			if (url.endsWith(eUrl)) {
				filterChain.doFilter(request, response);
				return;
			}
		}

		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession().getServletContext());
		UserController userController = wac.getBean(UserController.class);
		UserReq userReq = userController.getCurrentUser();

		if (userReq == null) {
			response.setContentType("application/json");

			FormReq formReq = new FormReq();
			formReq.setMsg("没有用户登录!");
			formReq.setError(true);

			response.getOutputStream().write(JSONObject.fromObject(formReq).toString().getBytes());

			return;
		}

		filterChain.doFilter(request, response);
	}

}
