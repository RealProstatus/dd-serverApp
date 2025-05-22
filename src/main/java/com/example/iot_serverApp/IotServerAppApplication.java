package com.example.iot_serverApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IotServerAppApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(IotServerAppApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET); // Указываем веб-приложение с сервлетами
		application.run(args);
	}
}