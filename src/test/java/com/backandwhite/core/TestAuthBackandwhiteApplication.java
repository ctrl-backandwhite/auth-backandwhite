package com.backandwhite.core;

import org.springframework.boot.SpringApplication;

public class TestAuthBackandwhiteApplication {

	public static void main(String[] args) {
		SpringApplication.from(AuthBackandwhiteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}
}
