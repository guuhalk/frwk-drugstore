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

import constants.ProductMethods;
import constants.RabbitMQConstants;
import dto.ProductDTO;
import model.Request;
import model.Response;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request request = new Request(ProductMethods.GET_ALL);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idProduct}")
	public ResponseEntity<?> findById(@PathVariable String idProduct) {
		Request request = new Request(ProductMethods.FIND_BY_ID, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
		Request request = new Request(ProductMethods.CREATE, productDTO);
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idProduct}")
	public ResponseEntity<?> update(@PathVariable String idProduct, @RequestBody ProductDTO productDTO) {
		Request request = new Request(ProductMethods.UPDATE, productDTO, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@DeleteMapping("/{idProduct}")
	public ResponseEntity<?> delete(@PathVariable String idProduct) {
		Request request = new Request(ProductMethods.DELETE, Arrays.asList(idProduct));
		Response response = rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, request);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
