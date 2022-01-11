package com.apigateway.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apigateway.service.RabbitMQService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.constants.UserMethods;
import com.msschemas.dto.MultipartFileDTO;
import com.msschemas.dto.PasswordInputDTO;
import com.msschemas.dto.UserDTO;
import com.msschemas.dto.UserWithPassWordDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(DefaultMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idUser}")
	public ResponseEntity<?> findById(@PathVariable Long idUser) {
		Request menssage = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{cpfUser}/cpf")
	public ResponseEntity<?> findByCPF(@PathVariable String cpfUser) {
		Request menssage = new Request(UserMethods.FIND_BY_CPF, Arrays.asList(cpfUser));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserWithPassWordDTO userWithPassWordDTO) {
		Request menssage = new Request(DefaultMethods.CREATE, userWithPassWordDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping("/{idUser}/save-photo")
	public ResponseEntity<?> savePhotoUser(@PathVariable Long idUser, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		MultipartFileDTO multipartFileDTO = new MultipartFileDTO(multipartFile.getOriginalFilename(), multipartFile.getBytes());
		
		Request menssage = new Request(UserMethods.SAVE_USER_PHOTO, multipartFileDTO, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idUser}")
	public ResponseEntity<?> update(@PathVariable Long idUser, @RequestBody @Valid UserDTO userDto) {
		Request menssage = new Request(DefaultMethods.UPDATE, userDto, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idUser}/password")
	public ResponseEntity<?> update(@PathVariable Long idUser, @RequestBody @Valid PasswordInputDTO passwordInputDTO) {
		Request menssage = new Request(UserMethods.CHANGE_PASSWORD, passwordInputDTO, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
		
	@DeleteMapping("/{idUser}")
	public ResponseEntity<?> delete(@PathVariable Long idUser) {
		Request menssage = new Request(DefaultMethods.DELETE, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_USER, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
}
