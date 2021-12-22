package com.authserver.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msschemas.dto.UserWithPassWordDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

	final AuthenticationManager authenticationManager;
	static final int EXPIRE_TOKEN = 600_000; 
	static final String PASSWORD_TOKEN = "1130f66f-591f-4f1c-a748-f1f9a1c7c43a"; 

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			
			UserWithPassWordDTO user = new ObjectMapper().readValue(request.getInputStream(), UserWithPassWordDTO.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getCpf(), user.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException("Fala ao autenticar usuário", e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		DetailUserDate userDetail = (DetailUserDate) authResult.getPrincipal();
		
		String token = JWT.create()
				.withSubject(userDetail.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TOKEN))
				.sign(Algorithm.HMAC512(PASSWORD_TOKEN));
		
		response.getWriter().write(token);
		response.getWriter().flush();
	
	}

}
