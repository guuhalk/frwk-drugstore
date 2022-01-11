package com.msschemas.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msschemas.model.enumeration.UserType;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 7835379498195684748L;

	private Long id;
	
	private String name; 
	
	private String cpf;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthday;
	
	private String photo;
	
	private String email;
	
	private UserType userType;
	
	private AddressDTO address;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;
}
