package com.geping.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.geping.etl.config.AppProfile;

@SpringBootApplication
public class DataETLApplication extends SpringBootServletInitializer{
	private static Logger logger = LoggerFactory.getLogger(DataETLApplication.class);
	
	public static void main(String[] args) {
		
		if (System.getProperty("spring.profiles.active") == null) {
			System.setProperty("spring.profiles.active", AppProfile.INTG);
		}
		logger.info("profiles:{}",System.getProperty("spring.profiles.active"));
		
		SpringApplication.run(DataETLApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(DataETLApplication.class);
		return super.configure(builder);
	}
}