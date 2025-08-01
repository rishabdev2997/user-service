package com.example.user_service;
import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories

public class UserServiceApplication {

	public static void main(String[] args) {
		var ctx = SpringApplication.run(UserServiceApplication.class, args);
		String jwtSecret = ctx.getEnvironment().getProperty("custom.jwt.secret");
	}

}
