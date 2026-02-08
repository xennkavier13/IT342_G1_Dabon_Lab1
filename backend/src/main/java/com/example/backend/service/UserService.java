package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<UserDto> getUserProfile(String username) {
		return userRepository.findByUsername(username)
				.map(this::toDto);
	}

	private UserDto toDto(User user) {
		return UserDto.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.createdAt(user.getCreatedAt())
				.build();
	}
}
