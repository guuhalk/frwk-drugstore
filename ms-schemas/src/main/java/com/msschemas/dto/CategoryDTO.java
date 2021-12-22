package com.msschemas.dto;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	private String id;
	
	private String name;
	
	private String description;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
