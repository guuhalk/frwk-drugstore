package com.msusers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.msusers.converter.UserConverter;
import com.msusers.model.User;
import com.msusers.repository.UserRepository;

import dto.UserDTO;
import exception.EntityInUseException;
import exception.EntityNotFoundException;
import exception.GenericException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserConverter userConverter;
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User findById(Long idUser) {
		return getOrThrowException(idUser);
	}
	
	public User create(User user) {
		if(isEmailOrCpfAlreadyInUse(user.getEmail(), user.getCpf(), user.getId())) {
			throw new GenericException("This Email and/or CPF is already in use");
		}
		
		if(user.isNew()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		return userRepository.save(user);
	}
	
	public User update(Long idUser, UserDTO userDTO) {
		User storedUser = getOrThrowException(idUser);
		userDTO.setId(idUser);
		userDTO.setCreatedAt(storedUser.getCreatedAt());
		userConverter.copyToEntity(userDTO, storedUser);
		
		return create(storedUser);
	}
	
	public void changePassword(Long idUser, String currentPassword, String newPassword) {
		User user = getOrThrowException(idUser);
		
		if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
			throw new GenericException("The current password is incorrect.");
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	
	public void remove(Long idUser) {
		try {
			userRepository.deleteById(idUser);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("User not found");
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("User can't be removed.");
		}
	}
	
	public User getOrThrowException(Long idUser) {
		return userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found!"));
	}
	
	private boolean isEmailOrCpfAlreadyInUse(String userEmail, String userCpf, Long userId) {
		if(userId != null) {
			return userRepository.countByEmailOrCpfAndIdDiff(userEmail, userCpf, userId) > 0;
		}
		
		return userRepository.existsByEmailOrCpf(userEmail, userCpf);
	}
}