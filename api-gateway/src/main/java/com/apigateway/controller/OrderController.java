package com.apigateway.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.service.RabbitMQService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.OrderDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(DefaultMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_REQUESTS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idOrder}")
	public ResponseEntity<?> findById(@PathVariable Long idOrder) {
		Request menssage = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idOrder.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_REQUESTS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid OrderDTO orderDTO) {
		Request menssage = new Request(DefaultMethods.CREATE, orderDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_REQUESTS, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
}
