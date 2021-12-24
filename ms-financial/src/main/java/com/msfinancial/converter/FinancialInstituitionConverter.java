package com.msfinancial.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msfinancial.model.FinancialInstituition;
import com.msschemas.dto.FinancialInstituitionDTO;

@Component
public class FinancialInstituitionConverter {

	@Autowired
	private ModelMapper modelMapper;
 	
	public List<FinancialInstituitionDTO> toCollectionDto(List<FinancialInstituition> instituitions) {
		return instituitions.stream()
				.map(instituition -> toDto(instituition))
				.collect(Collectors.toList());
	}
	
	public FinancialInstituitionDTO toDto(FinancialInstituition instituition) {
 		return modelMapper.map(instituition, FinancialInstituitionDTO.class);
 	}
	
	public FinancialInstituition toEntity(FinancialInstituitionDTO instituitionDTO) {
		return modelMapper.map(instituitionDTO, FinancialInstituition.class);
	}
	
	public void copyToEntity(FinancialInstituitionDTO instituitionDTO, FinancialInstituition instituition) {
		modelMapper.map(instituitionDTO, instituition);
	}
}
