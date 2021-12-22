package com.msusers.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msschemas.dto.UserWithPassWordDTO;
import com.msusers.service.UserService;

@RestController
@RequestMapping("/users")
public class UserLoginController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	private Optional<UserWithPassWordDTO> getUserLogin( @RequestBody String cpf) {
		UserWithPassWordDTO userDTO = userService.getUserLogin(cpf);
		return userDTO == null ? null : Optional.of(userDTO); 
	}

}
