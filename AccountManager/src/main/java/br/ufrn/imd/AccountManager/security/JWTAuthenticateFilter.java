package br.ufrn.imd.AccountManager.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufrn.imd.AccountManager.data.DetailsUserData;
import br.ufrn.imd.AccountManager.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {
	public static final int TOKEN_EXPIRATION= 600_000;
	public static final String TOKEN_PASSWORD= "fee3c55d-b50a-4727-b3f0-000ff991457a";
	
	private final AuthenticationManager authenticationManager;

	public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					user.getLogin(),
					user.getPassword(),
					new ArrayList<>()
					));

		}catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Falha ao Autenticar	usuario",e);
		}
				
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		DetailsUserData  userData= (DetailsUserData)authResult.getPrincipal();
		
		String token = JWT.create().withSubject(userData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
				.sign(Algorithm.HMAC512(TOKEN_PASSWORD));
		
		response.getWriter().write(token);
		response.getWriter().flush();
	}
	
}
