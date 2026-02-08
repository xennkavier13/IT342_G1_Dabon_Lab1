package com.example.backend.controller;

import com.example.backend.config.TokenProvider;
import com.example.backend.dto.UserDto;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	private final UserService userService;
	private final TokenProvider tokenProvider;

	public UserController(UserService userService, TokenProvider tokenProvider) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}

	@GetMapping("/user/me")
	public ResponseEntity<?> getCurrentUser(
			@RequestHeader(value = "Authorization", required = false) String authorization
	) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			return ResponseEntity.status(401).body("Missing or invalid Authorization header");
		}

		String token = authorization.substring("Bearer ".length()).trim();
		if (token.isEmpty() || !tokenProvider.isTokenValid(token)) {
			return ResponseEntity.status(401).body("Invalid or expired token");
		}

		String username = tokenProvider.getUsernameFromToken(token);
		Optional<UserDto> user = userService.getUserProfile(username);

		return user
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(404).body("User not found"));
	}
}
