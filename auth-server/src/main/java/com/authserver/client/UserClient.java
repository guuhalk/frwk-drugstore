package com.authserver.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import dto.UserWithPassWordDTO;

@FeignClient(value = "user", url="http://localhost:8083")
public interface UserClient {

	@PostMapping("/users")
	Optional<UserWithPassWordDTO> userLoginByCpf(String cpf);
	
	
}
