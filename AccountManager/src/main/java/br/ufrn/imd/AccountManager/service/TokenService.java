package br.ufrn.imd.AccountManager.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.ufrn.imd.AccountManager.model.User;

@Service
public class TokenService {
	public String gerarToken(User user) {
		return JWT.create()
				.withIssuer("User")
				.withSubject(user.getUsername())
				.withClaim("id", user.getId())
				.withExpiresAt(LocalDateTime.now()
						.plusMinutes(10)
						.toInstant(ZoneOffset.of("-03:00"))
						
						).sign(Algorithm.HMAC512("Secreta"));
	}
}
