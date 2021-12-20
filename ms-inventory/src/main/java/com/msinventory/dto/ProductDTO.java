package com.msinventory.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.msinventory.model.Category;
import com.msinventory.model.enumeration.ProductStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private String id;
	
	private String sku;
	
	private String name;
	
	private String description;
	
	private String photoPath;
	
	private BigDecimal price;
	
	private Long inventoryQuantity;
	
	private ProductStatus status;
	
	private Category category;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
