package com.msinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msinventory.converter.CategoryConverter;
import com.msinventory.model.Category;
import com.msinventory.repository.CategoryRepository;
import com.msinventory.service.CategoryService;

import dto.CategoryDTO;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryConverter categoryConverter;
	
	@GetMapping
	public List<CategoryDTO> getAll() {
		return categoryConverter.toCollectionDto(categoryRepository.findAll());
	}
	
	@GetMapping("/{idCategory}")
	public CategoryDTO findById(@PathVariable String idCategory) {
		Category category = categoryService.getOrThrowException(idCategory);
		
		return categoryConverter.toDto(category);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
		Category category = categoryConverter.toEntity(categoryDTO);
		category = categoryService.create(category);

		return categoryConverter.toDto(category);
	}
	
	@PutMapping("/{idCategory}")
	public CategoryDTO update(@PathVariable String idCategory, @RequestBody CategoryDTO categoryDTO) throws Exception {
		Category category = categoryService.getOrThrowException(idCategory);
		categoryDTO.setId(idCategory);
		categoryConverter.copyToEntity(categoryDTO, category);
		
		return categoryConverter.toDto(categoryService.create(category));
	}
	
	@DeleteMapping("/{idCategory}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String idCategory) {
		categoryService.remove(idCategory);
	}
}
