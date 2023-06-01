package br.ufrn.imd.AccountManager.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTValidateFilter extends BasicAuthenticationFilter {
	
	public final static String HEAD_ATTRIBUTE = "Authorization";
	public final static String ATTRIBUTE_PREFIX = "Bearer ";
	
	public JWTValidateFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String attribute = request.getHeader(HEAD_ATTRIBUTE);
		
		if(attribute == null) {
			chain.doFilter(request, response);
			return; 
		}
		
		if(!attribute.startsWith(ATTRIBUTE_PREFIX)) {
			chain.doFilter(request, response);
			return;  
		}
		String token = attribute.replace(ATTRIBUTE_PREFIX, "");
		
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		chain.doFilter(request, response);
	
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String user = JWT.require(Algorithm.HMAC512(JWTAuthenticateFilter.TOKEN_PASSWORD))
				.build()
				.verify(token)
				.getSubject();
		if(user == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	}

}
