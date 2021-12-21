package com.msinventory.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.msinventory.model.Category;
import com.msinventory.repository.CategoryRepository;

import exception.EntityAlreadyExistsException;
import exception.EntityInUseException;
import exception.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

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
