package com.msinventory.model;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Document
public class Category {

	@EqualsAndHashCode.Include
	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	@CreatedDate
	private OffsetDateTime createdAt;
	
	@LastModifiedDate
	private OffsetDateTime updatedAt;

}
