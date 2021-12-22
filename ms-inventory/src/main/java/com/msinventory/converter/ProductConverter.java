package com.msinventory.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msinventory.model.Product;
import com.msschemas.dto.ProductDTO;

@Component
public class ProductConverter {

	@Autowired
	private ModelMapper modelMapper;
 	
	public List<ProductDTO> toCollectionDto(List<Product> products) {
		return products.stream()
				.map(product -> toDto(product))
				.collect(Collectors.toList());
	}
	
	public ProductDTO toDto(Product product) {
 		return modelMapper.map(product, ProductDTO.class);
 	}
	
	public Product toEntity(ProductDTO productDTO) {
		return modelMapper.map(productDTO, Product.class);
	}
	
	public void copyToEntity(ProductDTO productDTO, Product product) {
		modelMapper.map(productDTO, product);
	}
}
