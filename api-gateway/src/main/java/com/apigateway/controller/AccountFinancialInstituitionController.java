package com.apigateway.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.apigateway.service.RabbitMQService;
import com.msschemas.constants.AccountFinancialInstituitionMethods;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.AccountFinancialInstituitionDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/accounts")
public class AccountFinancialInstituitionController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(DefaultMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idAccount}")
	public ResponseEntity<?> findById(@PathVariable Long idAccount) {
		Request menssage = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idAccount.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/by-users/{idUser}")
	public ResponseEntity<?> getAllByIdUser(@PathVariable Long idUser) {
		Request menssage = new Request(AccountFinancialInstituitionMethods.GET_ALL_BY_ID_USER, Arrays.asList(idUser.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid AccountFinancialInstituitionDTO accountDTO) {
		Request menssage = new Request(DefaultMethods.CREATE, accountDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idAccount}")
	public ResponseEntity<?> update(@PathVariable Long idAccount, @RequestBody @Valid AccountFinancialInstituitionDTO accountDTO) {
		Request menssage = new Request(DefaultMethods.UPDATE, accountDTO, Arrays.asList(idAccount.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
		
	@DeleteMapping("/{idAccount}")
	public ResponseEntity<?> delete(@PathVariable Long idAccount) {
		Request menssage = new Request(DefaultMethods.DELETE, Arrays.asList(idAccount.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_ACCOUNTS, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
