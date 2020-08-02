package com.citi.sanctions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.citi.sanctions.repository")
@SpringBootApplication

public class SanctionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanctionsApplication.class, args);
	}

}
