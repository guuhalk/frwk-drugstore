package com.msusers.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msusers.converter.UserConverter;
import com.msusers.model.User;
import com.msusers.service.UserService;

import constants.RabbitMQConstants;
import constants.UserMethods;
import dto.UserDTO;
import exception.EntityInUseException;
import exception.EntityNotFoundException;
import exception.GenericException;
import model.Request;
import model.Response;

@Component
public class UserConsumer {

	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private UserService userService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_USER)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
			
			if(UserMethods.GET_ALL.equals(nameRequest)) {
				List<UserDTO> usersDto = userConverter.toCollectionDto(userService.getAll());
				response.setBody(usersDto);
				response.setResponseCode(200);
				
			} else if(UserMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(userConverter.toDto(userService.findById(idUser)));
				response.setResponseCode(200);
				
			} else if(UserMethods.CREATE.equals(nameRequest)) {
				UserDTO userDTO = (UserDTO) request.getBody();
				userDTO = userConverter.toDto(userService.create(userConverter.toEntity(userDTO)));
				response.setBody(userDTO);
				response.setResponseCode(201);
				
			} else if(UserMethods.UPDATE.equals(nameRequest)) {
				UserDTO userDTO = (UserDTO) request.getBody();
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				User user = userService.update(idUser, userDTO);
				response.setBody(userConverter.toDto(user));
				response.setResponseCode(200);
				
			} else if(UserMethods.DELETE.equals(nameRequest)) {
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				userService.remove(idUser);
				response.setResponseCode(204);
			}
			
		} catch (EntityInUseException e) {
			response.setResponseCode(409);
			response.setBody(e.getMessage());
		
		} catch (EntityNotFoundException e) {
			response.setResponseCode(404);
			response.setBody(e.getMessage());
		
		} catch (GenericException e) {
			response.setResponseCode(400);
			response.setBody(e.getMessage());
		
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setBody(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
}
