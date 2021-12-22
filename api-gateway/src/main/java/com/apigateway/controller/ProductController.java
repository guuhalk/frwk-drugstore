package com.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

