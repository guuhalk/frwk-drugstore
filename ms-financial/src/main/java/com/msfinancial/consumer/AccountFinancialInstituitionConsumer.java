package com.msfinancial.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msfinancial.converter.AccountFinancialInstituitionConverter;
import com.msfinancial.model.AccountFinancialInstituition;
import com.msfinancial.service.AccountFinancialInstituitionService;
import com.msschemas.constants.AccountFinancialInstituitionMethods;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.AccountFinancialInstituitionDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class AccountFinancialInstituitionConsumer {

	@Autowired
	private AccountFinancialInstituitionConverter accountConverter;
	
	@Autowired
	private AccountFinancialInstituitionService accountService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_ACCOUNTS)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
		
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<AccountFinancialInstituitionDTO> accountsDTO = accountConverter.toCollectionDto(accountService.getAll());
				response.setBody(accountsDTO);
				response.setResponseCode(200);

			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idAccount = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(accountConverter.toDto(accountService.findById(idAccount)));
				response.setResponseCode(200);
				
			} else if(AccountFinancialInstituitionMethods.GET_ALL_BY_ID_USER.equals(nameRequest)) {
				Long idUser = Long.valueOf(request.getPathVariables().get(0));
				List<AccountFinancialInstituitionDTO> accountsDTO = accountConverter.toCollectionDto(accountService.getAllByIdUser(idUser));
				response.setBody(accountsDTO);
				response.setResponseCode(200);
			
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				AccountFinancialInstituitionDTO accountDTO = (AccountFinancialInstituitionDTO) request.getBody();
				AccountFinancialInstituition account = accountService.create(accountConverter.toEntity(accountDTO));
				accountDTO = accountConverter.toDto(account);
				response.setBody(accountDTO);
				response.setResponseCode(201);
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				AccountFinancialInstituitionDTO accountDTO = (AccountFinancialInstituitionDTO) request.getBody();
				Long idAccount = Long.valueOf(request.getPathVariables().get(0));
				AccountFinancialInstituition account = accountService.update(idAccount, accountDTO);
				response.setBody(accountConverter.toDto(account));
				response.setResponseCode(200);
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				Long idAccount = Long.valueOf(request.getPathVariables().get(0));
				accountService.remove(idAccount);
				response.setResponseCode(204);
			}
			
		} catch (EntityInUseException e) {
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
		}
		
		return response;
	}
}
