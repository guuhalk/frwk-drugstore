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
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.FinancialInstituitionDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/financial-instituitions")
public class FinancialInstituitionController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(DefaultMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idInstituition}")
	public ResponseEntity<?> findById(@PathVariable Long idInstituition) {
		Request menssage = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idInstituition.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid FinancialInstituitionDTO instituitionDTO) {
		Request menssage = new Request(DefaultMethods.CREATE, instituitionDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idInstituition}")
	public ResponseEntity<?> update(@PathVariable Long idInstituition, @RequestBody @Valid FinancialInstituitionDTO instituitionDTO) {
		Request menssage = new Request(DefaultMethods.UPDATE, instituitionDTO, Arrays.asList(idInstituition.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
		
	@DeleteMapping("/{idInstituition}")
	public ResponseEntity<?> delete(@PathVariable Long idInstituition) {
		Request menssage = new Request(DefaultMethods.DELETE, Arrays.asList(idInstituition.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
