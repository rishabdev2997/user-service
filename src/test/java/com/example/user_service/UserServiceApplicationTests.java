package com.example.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties =
		"spring.security.oauth2.resourceserver.jwt.secret="
				+ "Vg7$T#xZ@9Pq*Lm6!e2FbDnRkYw0zCuA")
class UserServiceApplicationTests {

	@Test
	void contextLoads() { }
}
