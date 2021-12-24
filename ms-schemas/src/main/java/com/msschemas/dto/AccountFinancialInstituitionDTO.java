package com.msschemas.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountFinancialInstituitionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank
	private String account;
	
	@NotBlank
	private String agency;
	
	@NotNull
	private Long clientId;
	
	@NotNull
	private FinancialInstituitionDTO financialInstituition;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;
}
