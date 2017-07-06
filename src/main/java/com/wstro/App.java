package com.wstro;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = { "com.wstro" })
public class App extends SpringBootServletInitializer {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.setBannerMode(Mode.CONSOLE);
		app.run(App.class, args);
	}

	/**
	 * 部署Tomcat
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}

}
