package com.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.constants.RabbitMQConstants;
import com.apigateway.dto.UserDTO;
import com.apigateway.service.RabbitMQService;

@RestController
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@PutMapping
	public ResponseEntity alterUser(UserDTO userDto ){
		
		this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, userDto);
		return new ResponseEntity(HttpStatus.OK); 
	}
	
	
}
