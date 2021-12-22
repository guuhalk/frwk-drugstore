package com.msinventory.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msinventory.converter.ProductConverter;
import com.msinventory.model.Category;
import com.msinventory.model.Product;
import com.msinventory.repository.ProductRepository;

import dto.ProductDTO;
import exception.EntityAlreadyExistsException;
import exception.EntityInUseException;
import exception.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	public List<Product> getAll() {
		return productRepository.findAll();
	}
	
	public Product findById(String idProduct) {
		return getOrThrowException(idProduct);
	}

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
	
	public Product update(String idProduct, ProductDTO productDTO) {
		Product storedProduct = getOrThrowException(idProduct);
		productDTO.setId(idProduct);
		productDTO.setCreatedAt(storedProduct.getCreatedAt());
		productConverter.copyToEntity(productDTO, storedProduct);

		return create(storedProduct);
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
