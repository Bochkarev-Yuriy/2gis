package ru.two.gis.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{AppConfig.class};
	}

	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[]{};
	}

	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
