package com.example.backend.config;

import com.example.backend.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class TokenProvider {

	private final Key signingKey;
	private final long jwtExpirationMs;

	public TokenProvider(
			@Value("${jwt.secret}") String jwtSecret,
			@Value("${jwt.expiration}") long jwtExpirationMs
	) {
		this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		this.jwtExpirationMs = jwtExpirationMs;
	}

	public String generateToken(User user) {
		Instant now = Instant.now();
		Instant expiration = now.plusMillis(jwtExpirationMs);

		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("userId", user.getUserId())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(expiration))
				.signWith(signingKey, SignatureAlgorithm.HS256)
				.compact();
	}
}
