package com.msfinancial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.msfinancial.converter.FinancialInstituitionConverter;
import com.msfinancial.model.FinancialInstituition;
import com.msfinancial.repository.FinancialInstituitionRepository;
import com.msschemas.dto.FinancialInstituitionDTO;
import com.msschemas.exception.EntityAlreadyExistsException;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;

@Service
public class FinancialInstituitionService {

	@Autowired
	private FinancialInstituitionRepository instituitionRepository;
	
	@Autowired
	private FinancialInstituitionConverter instituitionConverter;
	
	public List<FinancialInstituition> getAll() {
		return instituitionRepository.findAll();
	}
	
	public FinancialInstituition findById(Long idInstituition) {
		return getOrThrowException(idInstituition);
	}
	
	public FinancialInstituition create(FinancialInstituition financialInstituition) {
		if(isFinancialInstituitionAlreadyExists(financialInstituition.getCode(), financialInstituition.getId())) {
			throw new EntityAlreadyExistsException("Financial Instituition already exists");
		}
		
		return instituitionRepository.save(financialInstituition);
	}
	
	public FinancialInstituition update(Long idInstituition, FinancialInstituitionDTO instituitionDTO) {
		FinancialInstituition storedInstituition = getOrThrowException(idInstituition);
		instituitionDTO.setId(idInstituition);
		instituitionDTO.setCreatedAt(storedInstituition.getCreatedAt());
		instituitionConverter.copyToEntity(instituitionDTO, storedInstituition);

		return create(storedInstituition);
	}
	
	public void remove(Long idFinancialInstituition) {
		try {
			instituitionRepository.deleteById(idFinancialInstituition);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Financial Instituition not found");
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("Financial Instituition can't be removed.");
		}
	}
	
	public FinancialInstituition getOrThrowException(Long idFinancialInstituition) {
		return instituitionRepository.findById(idFinancialInstituition).orElseThrow(() -> 
						new EntityNotFoundException("Financial Instituition not found!"));
	}
	
	private boolean isFinancialInstituitionAlreadyExists(String code, Long id) {
		if(id != null) {
			return instituitionRepository.countByCodeAndIdDiff(code, id) > 0;
		}
		
		return instituitionRepository.existsByCode(code);
	}
}
