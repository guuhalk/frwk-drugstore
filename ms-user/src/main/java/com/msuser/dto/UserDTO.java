package com.msuser.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.msuser.model.Address;
import com.msuser.model.User;
import com.msuser.model.enums.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

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
	
	public static UserDTO create (User user) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(user, UserDTO.class);	
	}
	
	public List<UserDTO> toCollectionModel(List<User> users) {
		ModelMapper modelMapper = new ModelMapper();
		return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
	}
	
	
	
}
