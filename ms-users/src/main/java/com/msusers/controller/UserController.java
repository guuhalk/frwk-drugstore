package com.msusers.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msusers.converter.UserConverter;
import com.msusers.model.User;
import com.msusers.repository.UserRepository;
import com.msusers.service.UserService;

import dto.UserDTO;
import dto.UserWithPassWordDTO;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;
	
	@GetMapping
	public List<UserDTO> getAll() {
		return userConverter.toCollectionDto(userRepository.findAll());
	}
	
	@GetMapping("/{idUser}")
	public UserDTO findById(@PathVariable Long idUser) {
		User user = userService.getOrThrowException(idUser);
		
		return userConverter.toDto(user);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO create(@RequestBody @Valid UserWithPassWordDTO userInput) {
		User user = userConverter.toEntity(userInput);
		user = userService.create(user);
		
		return userConverter.toDto(user);
	}
	
//	@PutMapping("/{idUser}/password")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void changePassword(@PathVariable Long idUser, @RequestBody @Valid PasswordInput passwordInput) {
//		userService.changePassword(idUser, passwordInput.getCurrentPassword(), passwordInput.getNewPassword());
//	}
	
	@PutMapping("/{idUser}")
	public UserDTO update(@PathVariable Long idUser, @RequestBody @Valid UserDTO userInput) {
		User user = userService.getOrThrowException(idUser);
		userConverter.copyToEntity(userInput, user);
		
		return userConverter.toDto(userService.create(user));
	}
	
	@DeleteMapping("/{idUser}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idUser) {
		userService.remove(idUser);
	}
	
}
