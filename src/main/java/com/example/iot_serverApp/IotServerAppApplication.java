package com.example.iot_serverApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IotServerAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IotServerAppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IotServerAppApplication.class);
	}
}
