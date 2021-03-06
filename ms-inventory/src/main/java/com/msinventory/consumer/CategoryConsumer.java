package com.msinventory.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.msinventory.converter.CategoryConverter;
import com.msinventory.model.Category;
import com.msinventory.service.CategoryService;
import com.msschemas.constants.DefaultMethods;
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
			
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<CategoryDTO> categoriesDTO = categoryConverter.toCollectionDto(categoryService.getAll());
				response.setBody(categoriesDTO);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				String idCategory = request.getPathVariables().get(0);
				response.setBody(categoryConverter.toDto(categoryService.findById(idCategory)));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				CategoryDTO categoryDTO = (CategoryDTO) request.getBody();
				categoryDTO = categoryConverter.toDto(categoryService.create(categoryConverter.toEntity(categoryDTO)));
				response.setBody(categoryDTO);
				response.setResponseCode(HttpStatus.CREATED.value());
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				CategoryDTO categoryDTO = (CategoryDTO) request.getBody();
				String idCategory = request.getPathVariables().get(0);
				Category category = categoryService.update(idCategory, categoryDTO);
				response.setBody(categoryConverter.toDto(category));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				String idCategory = request.getPathVariables().get(0);
				categoryService.remove(idCategory);
				response.setResponseCode(HttpStatus.NO_CONTENT.value());
			}
			
		}  catch (EntityInUseException e) {
			response.setResponseCode(HttpStatus.CONFLICT.value());
			response.setBody(e.getMessage());
		
		} catch (EntityNotFoundException e) {
			response.setResponseCode(HttpStatus.NOT_FOUND.value());
			response.setBody(e.getMessage());
		
		} catch (GenericException e) {
			response.setResponseCode(HttpStatus.BAD_REQUEST.value());
			response.setBody(e.getMessage());
		
		} catch (Exception e) {
			response.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setBody(e.getMessage());
		}
		
		return response;
	}
}
