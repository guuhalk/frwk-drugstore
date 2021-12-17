package com.msuser.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.msuser.dto.UserDTO;
import com.msuser.exception.GenericException;
import com.msuser.model.User;
import com.msuser.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserDTO getUserById(Long idUser) {
		User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found!"));
		new UserDTO();
		return UserDTO.create(user);
	}
	
	
	public UserDTO create(User user) {
		if(isEmailAlreadyInUse(user.getEmail(), user.getId())) {
			throw new GenericException("This email is already in use");
		}
		
		if(isCpfAlreadyInUse(user.getCpf(), user.getId())) {
			throw new GenericException("This CPF is already in use");
		}
		
		if(user.isNew()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		User userSaved = userRepository.save(user);
		new UserDTO();
		
		return userSaved != null ? UserDTO.create(userSaved) : null;
	}
	
	private boolean isEmailAlreadyInUse(String userEmail, Long userId) {
		if(userId != null) {
			return userRepository.countByEmailAndIdDiff(userEmail, userId) > 0;
		}
		
		return userRepository.existsByEmail(userEmail);
	}
	
	private boolean isCpfAlreadyInUse(String userCpf, Long userId) {
		if(userId != null) {
			return userRepository.countByCpfAndIdDiff(userCpf, userId) > 0;
		}
		
		return userRepository.existsByCpf(userCpf);
	}
}
