package com.msinventory.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.msinventory.converter.ProductConverter;
import com.msinventory.model.Product;
import com.msinventory.service.ProductService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.ProductDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class ProductConsumer {

	@Autowired
	private ProductConverter productConverter;
	
	@Autowired
	private ProductService productService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_PRODUCTS)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
			
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<ProductDTO> productsDTO = productConverter.toCollectionDto(productService.getAll());
				response.setBody(productsDTO);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				String idProduct = request.getPathVariables().get(0);
				response.setBody(productConverter.toDto(productService.findById(idProduct)));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				ProductDTO productDTO = (ProductDTO) request.getBody();
				productDTO = productConverter.toDto(productService.create(productConverter.toEntity(productDTO)));
				response.setBody(productDTO);
				response.setResponseCode(HttpStatus.CREATED.value());
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				ProductDTO productDTO = (ProductDTO) request.getBody();
				String idProduct = request.getPathVariables().get(0);
				Product product = productService.update(idProduct, productDTO);
				response.setBody(productConverter.toDto(product));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				String idProduct = request.getPathVariables().get(0);
				productService.remove(idProduct);
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
