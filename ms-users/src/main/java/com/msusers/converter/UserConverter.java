package com.msusers.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msschemas.dto.UserDTO;
import com.msschemas.dto.UserWithPassWordDTO;
import com.msusers.model.User;

@Component
public class UserConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public List<UserDTO> toCollectionDto(List<User> users) {
		return users.stream()
				.map(user -> toDto(user))
				.collect(Collectors.toList());
	}
	
	public UserDTO toDto(User user) {
 		return modelMapper.map(user, UserDTO.class);
 	}
	
	public User toEntity(UserDTO userDTO) {
		return modelMapper.map(userDTO, User.class);
	}
	
	public void copyToEntity(UserDTO userDTO, User user) {
		modelMapper.map(userDTO, user);
	}
	
	public void updateEntity(User sourceUser, User destinationUser) {
		modelMapper.map(sourceUser, destinationUser);
	}
	
	public UserWithPassWordDTO toDtoWithPassWord(User user) {
 		return modelMapper.map(user, UserWithPassWordDTO.class);
 	}
}
