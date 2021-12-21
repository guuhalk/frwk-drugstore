package com.msusers.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msusers.converter.UserConverter;
import com.msusers.model.User;
import com.msusers.service.UserService;

import constants.RabbitMQConstants;
import constants.UserMethods;
import dto.UserDTO;
import model.Request;
import model.Response;

@Component
public class UserConsumer {

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserService userService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_USER)
	private Object consummer(Request request) throws IOException, InterruptedException, TimeoutException {

		Response response = new Response();
		
		String nameRequest = request.getNameRequest();
		
		if(UserMethods.GET_ALL.equals(nameRequest)) {
			List<UserDTO> usersDto = userConverter.toCollectionDto(userService.getAll());
			response.setListObject(new ArrayList<>());
			usersDto.forEach(userDto -> response.getListObject().add(userDto));
		
		} else if(UserMethods.FIND_BY_ID.equals(nameRequest)) {
			Long idUser = Long.valueOf(request.getPathVariables().get(0));
			response.setObject(userConverter.toDto(userService.findById(idUser)));
			
		} else if(UserMethods.CREATE.equals(nameRequest)) {
			UserDTO userDTO = (UserDTO) request.getBody();
			userDTO = userConverter.toDto(userService.create(userConverter.toEntity(userDTO)));
			response.setObject(userDTO);
		
		} else if(UserMethods.UPDATE.equals(nameRequest)) {
			UserDTO userDTO = (UserDTO) request.getBody();
			Long idUser = Long.valueOf(request.getPathVariables().get(0));
			User user = userService.update(idUser, userConverter.toEntity(userDTO));
			response.setObject(userConverter.toDto(user));
			
		} else if(UserMethods.DELETE.equals(nameRequest)) {
			Long idUser = Long.valueOf(request.getPathVariables().get(0));
			userService.remove(idUser);
		}

		return response;
	}
}
