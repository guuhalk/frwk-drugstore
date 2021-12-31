package com.authserver.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.msschemas.dto.UserWithPassWordDTO;

@FeignClient(value = "user", url="http://ms-users:8087")
public interface UserClient {

	@PostMapping("/users")
	Optional<UserWithPassWordDTO> userLoginByCpf(String cpf);
	
	
}
