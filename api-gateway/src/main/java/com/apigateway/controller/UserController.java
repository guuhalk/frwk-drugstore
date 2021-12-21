package com.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.service.RabbitMQService;

import constants.RabbitMQConstants;
import dto.UserDTO;
import model.Request;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private RabbitMQService rabbitMqService;

	@PutMapping
	public ResponseEntity<Object> alterUser(@RequestBody UserDTO userDto) {
		Request menssage = new Request("alterUser",userDto);
		Object user = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		return ResponseEntity.ok(user);
	}

}
