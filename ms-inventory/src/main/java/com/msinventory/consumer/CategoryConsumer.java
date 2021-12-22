package com.msinventory.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msinventory.converter.CategoryConverter;
import com.msinventory.model.Category;
import com.msinventory.service.CategoryService;
import com.msschemas.constants.CategoryMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.CategoryDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class CategoryConsumer {

	@Autowired
	private CategoryConverter categoryConverter;
	
	@Autowired
	private CategoryService categoryService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_CATEGORY)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
			
			if(CategoryMethods.GET_ALL.equals(nameRequest)) {
				List<CategoryDTO> categoriesDTO = categoryConverter.toCollectionDto(categoryService.getAll());
				response.setBody(categoriesDTO);
				response.setResponseCode(200);
				
			} else if(CategoryMethods.FIND_BY_ID.equals(nameRequest)) {
				String idCategory = request.getPathVariables().get(0);
				response.setBody(categoryConverter.toDto(categoryService.findById(idCategory)));
				response.setResponseCode(200);
				
			} else if(CategoryMethods.CREATE.equals(nameRequest)) {
				CategoryDTO categoryDTO = (CategoryDTO) request.getBody();
				categoryDTO = categoryConverter.toDto(categoryService.create(categoryConverter.toEntity(categoryDTO)));
				response.setBody(categoryDTO);
				response.setResponseCode(201);
				
			} else if(CategoryMethods.UPDATE.equals(nameRequest)) {
				CategoryDTO categoryDTO = (CategoryDTO) request.getBody();
				String idCategory = request.getPathVariables().get(0);
				Category category = categoryService.update(idCategory, categoryDTO);
				response.setBody(categoryConverter.toDto(category));
				response.setResponseCode(200);
				
			} else if(CategoryMethods.DELETE.equals(nameRequest)) {
				String idCategory = request.getPathVariables().get(0);
				categoryService.remove(idCategory);
				response.setResponseCode(204);
			}
			
		}  catch (EntityInUseException e) {
			response.setResponseCode(409);
			response.setBody(e.getMessage());
		
		} catch (EntityNotFoundException e) {
			response.setResponseCode(404);
			response.setBody(e.getMessage());
		
		} catch (GenericException e) {
			response.setResponseCode(400);
			response.setBody(e.getMessage());
		
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setBody(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
}
