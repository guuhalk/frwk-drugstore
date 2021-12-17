package com.authserver.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateEncodedPassword {

	public String encoder (String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
