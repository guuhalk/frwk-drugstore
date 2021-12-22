package com.msusers.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msusers.service.UserService;

import dto.UserWithPassWordDTO;

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
