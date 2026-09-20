package com.dash.dashboard;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class DashboardApplication {

	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

}
