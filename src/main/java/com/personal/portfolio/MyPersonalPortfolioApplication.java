package com.personal.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyPersonalPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPersonalPortfolioApplication.class, args);
	}

}
