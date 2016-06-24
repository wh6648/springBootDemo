package com.rijia.workPlatform.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rijia.workPlatform.filter.APIFilter;
import com.rijia.workPlatform.filter.APIListener;
import com.rijia.workPlatform.filter.APIServlet;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addStatusController("/403", HttpStatus.FORBIDDEN);
	}

	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	@Bean
	public ServletRegistrationBean getAPIServlet() {
		APIServlet apiServlet = new APIServlet();

		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(apiServlet);

		List<String> urlMappings = new ArrayList<String>();
		urlMappings.add("/apiServlet");// 访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);

		registrationBean.setLoadOnStartup(1);

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean getAPIFilter() {
		APIFilter apiFilter = new APIFilter();

		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(apiFilter);

		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");// 拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);

		registrationBean.setOrder(1);

		return registrationBean;
	}

	@Bean
	public ServletListenerRegistrationBean<EventListener> getAPIListener() {
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new APIListener());

		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		// registration.getUrlMappings().clear();
		// registration.addUrlMappings("*.do");
		return registration;
	}
}
