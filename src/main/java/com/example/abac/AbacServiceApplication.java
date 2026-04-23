package com.example.abac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AbacServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AbacServiceApplication.class, args);
	}
}