package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class PasswordEncoderConfig {

	@Bean
	public org.springframework.security.crypto.password.PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}