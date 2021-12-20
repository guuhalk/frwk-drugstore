package com.msinventory.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msinventory.exception.EntityAlreadyExistsException;
import com.msinventory.exception.EntityInUseException;
import com.msinventory.exception.EntityNotFoundException;
import com.msinventory.model.Category;
import com.msinventory.model.Product;
import com.msinventory.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;

	public Product create(Product product) {
		if(isCategoryAlreadyExists(product)) {
			throw new EntityAlreadyExistsException("Product already exists");
		}
		
		Category category = categoryService.getOrThrowException(product.getCategory().getId());
		product.setCategory(category);
		if(product.getCreatedAt() == null) {
			product.setCreatedAt(OffsetDateTime.now());
		}
		product.setUpdatedAt(OffsetDateTime.now());
		
		return productRepository.save(product);
	}
	
	public void remove(String idProduct) {
		if(!productRepository.existsById(idProduct)) {
			throw new EntityNotFoundException("Product not found");
		}
		
		try {
			productRepository.deleteById(idProduct);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("Product can't be removed.");
		}
	}
	
	public Product getOrThrowException(String idProduct) {
		return productRepository.findById(idProduct).orElseThrow(() -> new EntityNotFoundException("Product not found!"));
	}
	
	private boolean isCategoryAlreadyExists(Product product) {
		if(product.getId() != null) {
			return productRepository.countBySkuAndIdDiff(product.getSku(), product.getId()) > 0;
		}
		
		return productRepository.existsBySku(product.getSku());
	}
}