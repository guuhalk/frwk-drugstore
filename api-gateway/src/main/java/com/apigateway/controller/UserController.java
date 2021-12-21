package com.apigateway.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.service.RabbitMQService;

import constants.RabbitMQConstants;
import constants.UserMethods;
import dto.UserDTO;
import dto.UserWithPassWordDTO;
import model.Request;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private RabbitMQService rabbitMqService;

	@GetMapping
	public ResponseEntity<Object> getAll() {
		Request menssage = new Request(UserMethods.GET_ALL);
		Object user = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{idUser}")
	public ResponseEntity<Object> findById(@PathVariable Long idUser) {
		Request menssage = new Request(UserMethods.FIND_BY_ID, Arrays.asList(idUser.toString()));
		Object user = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody @Valid UserWithPassWordDTO userWithPassWordDTO) {
		Request menssage = new Request(UserMethods.CREATE, userWithPassWordDTO);
		Object user = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PutMapping("/{idUser}")
	public ResponseEntity<Object> update(@PathVariable Long idUser, @RequestBody @Valid UserDTO userDto) {
		Request menssage = new Request(UserMethods.UPDATE, userDto, Arrays.asList(idUser.toString()));
		Object user = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/{idUser}")
	public ResponseEntity<?> delete(@PathVariable Long idUser) {
		Request menssage = new Request(UserMethods.DELETE, Arrays.asList(idUser.toString()));
		this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.noContent().build();
	}
	
}
