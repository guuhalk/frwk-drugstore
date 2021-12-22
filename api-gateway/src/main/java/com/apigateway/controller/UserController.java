package com.apigateway.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import model.Response;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private RabbitMQService rabbitMqService;

	@PutMapping
	public ResponseEntity<Object> alterUser(@RequestBody UserDTO userDto) {
		Request menssage = new Request("alterUser",userDto);
		Response user = (Response) this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		return ResponseEntity.ok(user);
    
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(UserMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());

	}
	
	@GetMapping("/{idUser}")
	public ResponseEntity<?> findById(@PathVariable Long idUser) {
		Request menssage = new Request(UserMethods.FIND_BY_ID, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserWithPassWordDTO userWithPassWordDTO) {
		Request menssage = new Request(UserMethods.CREATE, userWithPassWordDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idUser}")
	public ResponseEntity<?> update(@PathVariable Long idUser, @RequestBody @Valid UserDTO userDto) {
		Request menssage = new Request(UserMethods.UPDATE, userDto, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@DeleteMapping("/{idUser}")
	public ResponseEntity<?> delete(@PathVariable Long idUser) {
		Request menssage = new Request(UserMethods.DELETE, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
}
