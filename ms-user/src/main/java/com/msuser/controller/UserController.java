package com.msuser.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msuser.dto.UserDTO;
import com.msuser.model.User;
import com.msuser.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
  
	
	@GetMapping("/{idUser}")
	public ResponseEntity<UserDTO>getUserById(@PathVariable Long idUser) {
		UserDTO userDto = userService.getUserById(idUser);
		return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.notFound().build(); 
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid User user) {
		UserDTO userDto = userService.create(user);
		return userDto != null ? ResponseEntity.ok(userDto) : ResponseEntity.noContent().build();
	}
	
}
