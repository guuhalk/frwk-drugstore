package com.msdrugstores.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdrugstores.converter.DrugstoreConverter;
import com.msdrugstores.model.Drugstore;
import com.msdrugstores.repository.DrugstoreRepository;
import com.msdrugstores.service.DrugstoreService;
import com.msschemas.dto.DrugstoreDTO;

@RestController
@RequestMapping("/drugstores")
public class DrugstoreController {

	@Autowired
	private DrugstoreRepository drugstoreRepository;
	
	@Autowired
	private DrugstoreService drugstoreService;

	@Autowired
	private DrugstoreConverter drugstoreConverter;
	
	@GetMapping
	public List<DrugstoreDTO> getAll() {
		return drugstoreConverter.toCollectionDto(drugstoreRepository.findAll());
	}
	
	@GetMapping("/{idDrugstore}")
	public DrugstoreDTO findById(@PathVariable Long idDrugstore) {
		Drugstore drugstore = drugstoreService.getOrThrowException(idDrugstore);
		
		return drugstoreConverter.toDto(drugstore);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DrugstoreDTO create(@RequestBody @Valid DrugstoreDTO drugstoreInput) {
		Drugstore drugstore = drugstoreConverter.toEntity(drugstoreInput);
		drugstore = drugstoreService.create(drugstore);
		
		return drugstoreConverter.toDto(drugstore);
	}
	
	@PutMapping("/{idDrugstore}")
	public DrugstoreDTO update(@PathVariable Long idDrugstore, @RequestBody Map<String, Object> fields) {
		Drugstore drugstore = drugstoreService.getOrThrowException(idDrugstore);
		
		merge(fields, drugstore);
		
		return drugstoreConverter.toDto(drugstoreService.create(drugstore));
	}
	
	@DeleteMapping("/{idDrugstore}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idDrugstore) {
		drugstoreService.remove(idDrugstore);
	}
	
	private void merge(Map<String, Object> fields, Drugstore storedDrugstore) {
		ObjectMapper objectMapper = new ObjectMapper();
		Drugstore originDrugstore = objectMapper.convertValue(fields, Drugstore.class);
		
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Drugstore.class, key);
			field.setAccessible(true);
			
			Object newValue = ReflectionUtils.getField(field, originDrugstore);
			
			ReflectionUtils.setField(field, storedDrugstore, newValue);
		});
	}
}
