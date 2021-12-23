package com.apigateway.controller;

import java.util.Arrays;

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
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.CategoryDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request request = new Request(DefaultMethods.GET_ALL);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_CATEGORY, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idCategory}")
	public ResponseEntity<?> findById(@PathVariable String idCategory) {
		Request request = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idCategory));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_CATEGORY, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO) {
		Request request = new Request(DefaultMethods.CREATE, categoryDTO);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_CATEGORY, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idCategory}")
	public ResponseEntity<?> update(@PathVariable String idCategory, @RequestBody CategoryDTO categoryDTO) {
		Request request = new Request(DefaultMethods.UPDATE, categoryDTO, Arrays.asList(idCategory));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_CATEGORY, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@DeleteMapping("/{idCategory}")
	public ResponseEntity<?> delete(@PathVariable String idCategory) {
		Request request = new Request(DefaultMethods.DELETE, Arrays.asList(idCategory));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_CATEGORY, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
