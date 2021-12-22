package com.msinventory.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msinventory.converter.CategoryConverter;
import com.msinventory.model.Category;
import com.msinventory.repository.CategoryRepository;
import com.msschemas.dto.CategoryDTO;
import com.msschemas.exception.EntityAlreadyExistsException;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}
	
	public Category findById(String idCategory) {
		return getOrThrowException(idCategory);
	}

	public Category create(Category category) {
		if(isCategoryAlreadyExists(category)) {
			throw new EntityAlreadyExistsException("Category already exists");
		}
		
		if(category.getCreatedAt() == null) {
			category.setCreatedAt(OffsetDateTime.now());
		}
		
		category.setUpdatedAt(OffsetDateTime.now());
		
		return categoryRepository.save(category);
	}
	
	public Category update(String idCategory, CategoryDTO categoryDTO) {
		Category storedCategory = getOrThrowException(idCategory);
		categoryDTO.setId(idCategory);
		categoryDTO.setCreatedAt(storedCategory.getCreatedAt());
		categoryConverter.copyToEntity(categoryDTO, storedCategory);

		return create(storedCategory);
	}
	
	public void remove(String idCategory) {
		if(!categoryRepository.existsById(idCategory)) {
			throw new EntityNotFoundException("Category not found");
		}
		
		try {
			categoryRepository.deleteById(idCategory);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException("Category can't be removed.");
		}
	}
	
	public Category getOrThrowException(String idCategory) {
		return categoryRepository.findById(idCategory).orElseThrow(() -> new EntityNotFoundException("Category not found!"));
	}
	
	private boolean isCategoryAlreadyExists(Category category) {
		if(category.getId() != null) {
			return categoryRepository.countByNameAndIdDiff(category.getName(), category.getId()) > 0;
		}
		
		return categoryRepository.existsByName(category.getName());
	}
}
