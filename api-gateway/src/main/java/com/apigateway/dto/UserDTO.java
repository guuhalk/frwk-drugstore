package com.apigateway.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.apigateway.model.Address;
import com.apigateway.model.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {

private Long id;
	
	private String name;
	
	private String cpf;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthday;
	
	private String email;
	
	private UserType userType;
	
	private Address address;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt; 
}
