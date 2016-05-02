package pl.ubytes.controller;

import javax.transaction.Transactional;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Transactional
@Configuration
public class DBController {
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		final ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/db/*");
		return registrationBean;
	}
}