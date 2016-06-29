package com.rijia.workPlatform.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class APIListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("==>APIListener contextInitialized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("==>APIListener contextDestroyed");

	}
}
