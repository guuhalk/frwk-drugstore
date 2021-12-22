package com.apigateway.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
/*
import com.authserver.security.JWTAutenticationFilter;
import com.authserver.security.JWTValidateFilter;
import com.authserver.security.JwtUserDetailsService;*/

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//private final JwtUserDetailsService userService;
	//private final PasswordEncoder passwordEncoder;
	
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		//.antMatchers(HttpMethod.POST, "/login")
		//.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		//.addFilter(new JWTAutenticationFilter(authenticationManager()))
		.addFilter(new JWTValidateFilter(authenticationManager()))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors();
	}
	
	
}
