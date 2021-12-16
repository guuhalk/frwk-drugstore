package com.msuser.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msuser.dto.UserDTO;
import com.msuser.model.User;
import com.msuser.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	
	
	public UserDTO getUserById(Long idUser) {
		User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found!"));
		new UserDTO();
		return UserDTO.create(user);
	}
}
