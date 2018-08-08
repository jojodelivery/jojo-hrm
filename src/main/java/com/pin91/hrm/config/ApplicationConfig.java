package com.pin91.hrm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories("com.pin91.hrm.persistant.repository")
@EntityScan("com.pin91.hrm.persistant.domain")
@ComponentScan({ "com.pin91.hrm.controller", "com.pin91.hrm.helper", "com.pin91.hrm.persistant.domain",
	"com.pin91.hrm.persistant.dao","com.pin91.hrm.persistant.repository","com.pin91.hrm.persistant.impl",
	"com.pin91.hrm.utils",
	"com.pin91.hrm.transferobject","com.pin91.hrm.service","com.pin91.wallet.interceptor" })
@SpringBootApplication
@Import({JojoHrmConfig.class,JojoWebConfig.class})
@Configuration
public class ApplicationConfig extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationConfig.class);
	}
}