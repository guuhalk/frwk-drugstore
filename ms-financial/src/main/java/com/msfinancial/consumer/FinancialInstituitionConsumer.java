package com.msfinancial.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.msfinancial.converter.FinancialInstituitionConverter;
import com.msfinancial.model.FinancialInstituition;
import com.msfinancial.service.FinancialInstituitionService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.FinancialInstituitionDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class FinancialInstituitionConsumer {

	@Autowired
	private FinancialInstituitionConverter instituitionConverter;
	
	@Autowired
	private FinancialInstituitionService instituitionService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
		
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<FinancialInstituitionDTO> instituitionsDTO = instituitionConverter.toCollectionDto(instituitionService.getAll());
				response.setBody(instituitionsDTO);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idInstituition = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(instituitionConverter.toDto(instituitionService.findById(idInstituition)));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				FinancialInstituitionDTO instituitionDTO = (FinancialInstituitionDTO) request.getBody();
				FinancialInstituition instituition = instituitionService.create(instituitionConverter.toEntity(instituitionDTO));
				instituitionDTO = instituitionConverter.toDto(instituition);
				response.setBody(instituitionDTO);
				response.setResponseCode(HttpStatus.CREATED.value());
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				FinancialInstituitionDTO instituitionDTO = (FinancialInstituitionDTO) request.getBody();
				Long idInstituition = Long.valueOf(request.getPathVariables().get(0));
				FinancialInstituition instituition = instituitionService.update(idInstituition, instituitionDTO);
				response.setBody(instituitionConverter.toDto(instituition));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				Long idInstituition = Long.valueOf(request.getPathVariables().get(0));
				instituitionService.remove(idInstituition);
				response.setResponseCode(HttpStatus.NO_CONTENT.value());
			}
			
		} catch (EntityInUseException e) {
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
