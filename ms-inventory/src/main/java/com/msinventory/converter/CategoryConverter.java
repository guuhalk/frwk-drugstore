package com.msinventory.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msinventory.model.Category;
import com.msschemas.dto.CategoryDTO;

@Component
public class CategoryConverter {

	@Autowired
	private ModelMapper modelMapper;
 	
	public List<CategoryDTO> toCollectionDto(List<Category> categories) {
		return categories.stream()
				.map(category -> toDto(category))
				.collect(Collectors.toList());
	}
	
	public CategoryDTO toDto(Category category) {
 		return modelMapper.map(category, CategoryDTO.class);
 	}
	
	public Category toEntity(CategoryDTO categoryDTO) {
		return modelMapper.map(categoryDTO, Category.class);
	}
	
	public void copyToEntity(CategoryDTO categoryDto, Category category) {
		modelMapper.map(categoryDto, category);
	}
}
