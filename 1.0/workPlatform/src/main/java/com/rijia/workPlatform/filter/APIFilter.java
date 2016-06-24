package com.rijia.workPlatform.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class APIFilter extends OncePerRequestFilter {

	private String[] excludeUrls = {};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 不允许浏览器端或缓存服务器缓存当前页面信息。
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");// 浏览器和缓存服务器都不应该缓存页面信息
		response.addHeader("Cache-Control", "no-store");// 请求和响应的信息都不应该被存储在对方的磁盘系统中；
		response.addHeader("Cache-Control", "must-revalidate");// 客户机的每次请求，代理服务器必须想服务器验证缓存是否过时；

		String url = request.getRequestURL().toString();
		for (String eUrl : excludeUrls) {
			if (url.contains(eUrl)) {
				filterChain.doFilter(request, response);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

}
