package com.authserver.security;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authserver.client.UserClient;

import dto.UserWithPassWordDTO;

@Component
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserClient userClient;
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Optional<UserWithPassWordDTO> user = userClient.userLoginByCpf(cpf);
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("Usuário [ "+ cpf + " ] não encontrado.");
		}
		
		return new DetailUserDate(user);
	}
}

