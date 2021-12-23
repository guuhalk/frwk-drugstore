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
import com.msschemas.dto.ProductDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request request = new Request(DefaultMethods.GET_ALL);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idProduct}")
	public ResponseEntity<?> findById(@PathVariable String idProduct) {
		Request request = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
		Request request = new Request(DefaultMethods.CREATE, productDTO);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idProduct}")
	public ResponseEntity<?> update(@PathVariable String idProduct, @RequestBody ProductDTO productDTO) {
		Request request = new Request(DefaultMethods.UPDATE, productDTO, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@DeleteMapping("/{idProduct}")
	public ResponseEntity<?> delete(@PathVariable String idProduct) {
		Request request = new Request(DefaultMethods.DELETE, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
