package com.msdrugstores.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msdrugstores.model.Drugstore;
import com.msschemas.dto.DrugstoreDTO;

@Component
public class DrugstoreConverter {

	@Autowired
	private ModelMapper modelMapper;
 	
	public List<DrugstoreDTO> toCollectionDto(List<Drugstore> drugstores) {
		return drugstores.stream()
				.map(drugstore -> toDto(drugstore))
				.collect(Collectors.toList());
	}
	
	public DrugstoreDTO toDto(Drugstore drugstore) {
 		return modelMapper.map(drugstore, DrugstoreDTO.class);
 	}
	
	public Drugstore toEntity(DrugstoreDTO drugstoreDTO) {
		return modelMapper.map(drugstoreDTO, Drugstore.class);
	}
	
	public void copyToEntity(DrugstoreDTO drugstoreDTO, Drugstore drugstore) {
		modelMapper.map(drugstoreDTO, drugstore);
	}
}
