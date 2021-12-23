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
import com.msschemas.dto.DrugstoreDTO;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@RestController
@RequestMapping("/drugstores")
public class DrugstoreController {

	@Autowired
	private RabbitMQService rabbitMqService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		Request menssage = new Request(DefaultMethods.GET_ALL);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@GetMapping("/{idDrugstore}")
	public ResponseEntity<?> findById(@PathVariable Long idDrugstore) {
		Request menssage = new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(idDrugstore.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, menssage);

		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid DrugstoreDTO drugstoreDTO) {
		Request menssage = new Request(DefaultMethods.CREATE, drugstoreDTO);
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
	
	@PutMapping("/{idDrugstore}")
	public ResponseEntity<?> update(@PathVariable Long idDrugstore, @RequestBody @Valid DrugstoreDTO drugstoreDTO) {
		Request menssage = new Request(DefaultMethods.UPDATE, drugstoreDTO, Arrays.asList(idDrugstore.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, menssage);
	
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
		
	@DeleteMapping("/{idDrugstore}")
	public ResponseEntity<?> delete(@PathVariable Long idDrugstore) {
		Request menssage = new Request(DefaultMethods.DELETE, Arrays.asList(idDrugstore.toString()));
		Response response = this.rabbitMqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, menssage);
		
		return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
	}
}
