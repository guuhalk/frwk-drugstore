package com.msschemas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.msschemas.model.enumeration.ProductStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String sku;
	
	private String name;
	
	private String description;
	
	private String photoPath;
	
	private BigDecimal price;
	
	private Long inventoryQuantity;
	
	private ProductStatus status;
	
	private CategoryDTO category;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
