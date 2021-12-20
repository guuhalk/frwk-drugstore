package com.msinventory.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.msinventory.model.enumeration.ProductStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Document
public class Product {

	@EqualsAndHashCode.Include
	@Id
	private String id;
	
	private String sku;
	
	private String name;
	
	private String description;
	
	private String photoPath;
	
	private BigDecimal price;
	
	private Long inventoryQuantity;
	
	private ProductStatus status;
	
	@DBRef
	private Category category;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;
}
