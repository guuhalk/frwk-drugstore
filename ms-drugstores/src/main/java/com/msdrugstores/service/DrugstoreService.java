package com.msdrugstores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.msdrugstores.converter.DrugstoreConverter;
import com.msdrugstores.model.Drugstore;
import com.msdrugstores.repository.DrugstoreRepository;
import com.msschemas.dto.DrugstoreDTO;
import com.msschemas.exception.EntityAlreadyExistsException;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;

@Service
public class DrugstoreService {

	@Autowired
	private DrugstoreRepository drugstoreRepository;
	
	@Autowired
	private DrugstoreConverter drugstoreConverter;
	
	public List<Drugstore> getAll() {
		return drugstoreRepository.findAll();
	}
	
	public Drugstore findById(Long idDrugstore) {
		return getOrThrowException(idDrugstore);
	}
	
	public Drugstore create(Drugstore drugstore) {
		if(isDrugstoreAlreadyExists(drugstore)) {
			throw new EntityAlreadyExistsException("Drugstore already exists");
		}
		
		return drugstoreRepository.save(drugstore);
	}
	
	public Drugstore update(Long idDrugstore, DrugstoreDTO drugstoreDTO) {
		Drugstore storedDrugstore = getOrThrowException(idDrugstore);
		drugstoreDTO.setId(idDrugstore);
		drugstoreDTO.setCreatedAt(storedDrugstore.getCreatedAt());
		drugstoreConverter.copyToEntity(drugstoreDTO, storedDrugstore);

		return create(storedDrugstore);
	}
	
	public void remove(Long idDrugstore) {
		try {
			drugstoreRepository.deleteById(idDrugstore);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException("Drugstore not found");
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("Drugstore can't be removed.");
		}
	}
	
	public Drugstore getOrThrowException(Long idDrugstore) {
		return drugstoreRepository.findById(idDrugstore).orElseThrow(() -> new EntityNotFoundException("Drugstore not found!"));
	}
	
	private boolean isDrugstoreAlreadyExists(Drugstore drugstore) {
		if(drugstore.getId() != null) {
			return drugstoreRepository.countByCnpjAndIdDiff(drugstore.getCnpj(), drugstore.getId()) > 0;
		}
		
		return drugstoreRepository.existsByCnpj(drugstore.getCnpj());
	}
	
}
