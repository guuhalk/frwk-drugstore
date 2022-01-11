package com.msusers.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.constants.UserMethods;
import com.msschemas.dto.MultipartFileDTO;
import com.msschemas.dto.PasswordInputDTO;
import com.msschemas.dto.UserDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;
import com.msusers.converter.UserConverter;
import com.msusers.model.User;
import com.msusers.service.UserService;

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
			
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<UserDTO> usersDto = userConverter.toCollectionDto(userService.getAll());
				response.setBody(usersDto);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(userConverter.toDto(userService.findById(idUser)));
				response.setResponseCode(200);
				
			} else if(UserMethods.FIND_BY_CPF.equals(nameRequest)) {
				String cpfUser = request.getPathVariables().get(0);
				response.setBody(userConverter.toDto(userService.findByCpf(cpfUser)));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				UserDTO userDTO = (UserDTO) request.getBody();
				userDTO = userConverter.toDto(userService.create(userConverter.toEntity(userDTO)));
				response.setBody(userDTO);
				response.setResponseCode(HttpStatus.CREATED.value());
				
			} else if (UserMethods.SAVE_USER_PHOTO.equals(nameRequest)) {
				MultipartFileDTO multipartFileDTO = (MultipartFileDTO) request.getBody();
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				UserDTO userDTO = userConverter.toDto(userService.savePhotoUser(multipartFileDTO, idUser));
				response.setBody(userDTO);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				UserDTO userDTO = (UserDTO) request.getBody();
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				User user = userService.update(idUser, userDTO);
				response.setBody(userConverter.toDto(user));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(UserMethods.CHANGE_PASSWORD.equals(nameRequest)) {
				PasswordInputDTO passwordDTO = (PasswordInputDTO) request.getBody();
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				userService.changePassword(idUser, passwordDTO.getCurrentPassword(), passwordDTO.getNewPassword());
				response.setResponseCode(HttpStatus.NO_CONTENT.value());
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				userService.remove(idUser);
				response.setResponseCode(HttpStatus.NO_CONTENT.value());
			}
			
		} catch (EntityInUseException e) {
			response.setResponseCode(HttpStatus.CONFLICT.value());
			response.setBody(e.getMessage());
		
		} catch (EntityNotFoundException e) {
			response.setResponseCode(HttpStatus.NOT_FOUND.value());
			response.setBody(e.getMessage());
		
		} catch (GenericException e) {
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setBody(e.getMessage());
		
		} catch (Exception e) {
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setBody(e.getMessage());
		}

		return response;
	}
	
}
