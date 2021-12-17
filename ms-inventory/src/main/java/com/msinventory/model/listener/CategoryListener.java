package com.msinventory.model.listener;

import java.time.OffsetDateTime;

import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import com.msinventory.model.Category;

@Component
public class CategoryListener implements BeforeSaveCallback<Category> {

	@Override
	public Category onBeforeSave(Category category, Document document, String collection) {
		if(category.getCreatedAt() == null) {
			category.setCreatedAt(OffsetDateTime.now());
		}
		
		category.setUpdatedAt(OffsetDateTime.now());
		
		return category;
	}

	
}
