package com.apigateway.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.service.RabbitMQService;

import constants.RabbitMQConstants;
import dto.ProductDTO;

@RestController
@RequestMapping("/products")
public class ProductController {

//	@Autowired
//	private ProductRepository productRepository;
//	
//	@Autowired
//	private ProductService productService;
//	
//	@Autowired
//	private ProductConverter productConverter;
//	
//	private RabbitMQService rabbitMQService;
//	
//	@GetMapping
//	public ResponseEntity<List<Object>> getAll() {
//		List<Object> products = rabbitMQService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS);
//		return ResponseEntity.of(products);
//	}
//	
//	@GetMapping("/{idProduct}")
//	public ProductDTO findById(@PathVariable String idProduct) {
//		Product product = productService.getOrThrowException(idProduct);
//		
//		return productConverter.toDto(product);
//	}
//	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ProductDTO create(@RequestBody ProductDTO productDTO) {
//		Product product = productConverter.toEntity(productDTO);
//		product = productService.create(product);
//
//		return productConverter.toDto(product);
//	}
//	
//	@PutMapping("/{idProduct}")
//	public ProductDTO update(@PathVariable String idProduct, @RequestBody ProductDTO productDTO) throws Exception {
//		Product product = productService.getOrThrowException(idProduct);
//		productDTO.setId(idProduct);
//		productConverter.copyToEntity(productDTO, product);
//		
//		return productConverter.toDto(productService.create(product));
//	}
//	
//	@DeleteMapping("/{idProduct}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void delete(@PathVariable String idProduct) {
//		productService.remove(idProduct);
//	}
}

