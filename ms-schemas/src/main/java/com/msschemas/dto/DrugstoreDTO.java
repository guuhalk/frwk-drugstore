package com.msschemas.dto;

import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugstoreDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	@CNPJ
	@NotBlank
	private String cnpj;

	@Email
	private String email;
	
	private List<String> phones;
	
	private AddressDTO address;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;

}
