package com.msschemas.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinancialInstituitionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String code;
	
	private String description;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
