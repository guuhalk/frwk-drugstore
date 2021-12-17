package com.authserver.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authserver.model.User;



@Component
public class JwtUserDetailsService implements UserDetailsService {
	
	public List<User> listUser; 
	
	
	public JwtUserDetailsService(){
		listUser = new ArrayList<>();
		User user = new User((long) 1, "Daniel","11779016689","daniel@teste.com", "$2a$10$LET7kfo8wLySvOYKamv5yODm8yjsSEcogfnuOj//RfRsUF9wl05iu" );
		listUser.add(user);
	}
	

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		
				
		Optional<User> user = findUserByCpf(cpf);
		
		if(user == null) {
			throw new UsernameNotFoundException("Usuário [ "+ cpf + " ] não encontrado.");
		}
		
		return new DetailUserDate(user);
	}
	
	
	public Optional<User> findUserByCpf(String cpf) {
		
		Optional<User> userReturn = null;
		
		for (User user : listUser) {
			if(user.getCpf().equals(cpf)) {
				userReturn = Optional.of(user);
			}
		}
	
		return userReturn;		
	}
	
}

