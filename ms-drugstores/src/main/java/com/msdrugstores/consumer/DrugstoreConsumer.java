package com.msdrugstores.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msdrugstores.converter.DrugstoreConverter;
import com.msdrugstores.model.Drugstore;
import com.msdrugstores.service.DrugstoreService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.DrugstoreDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class DrugstoreConsumer {

	@Autowired
	private DrugstoreConverter drugstoreConverter;
	
	@Autowired
	private DrugstoreService drugstoreService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_PHARMACIES)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
			
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<DrugstoreDTO> drugstoresDTO = drugstoreConverter.toCollectionDto(drugstoreService.getAll());
				response.setBody(drugstoresDTO);
				response.setResponseCode(200);
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idDrugstore = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(drugstoreConverter.toDto(drugstoreService.findById(idDrugstore)));
				response.setResponseCode(200);
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				DrugstoreDTO drugstoreDTO = (DrugstoreDTO) request.getBody();
				Drugstore drugstore = drugstoreService.create(drugstoreConverter.toEntity(drugstoreDTO));
				drugstoreDTO = drugstoreConverter.toDto(drugstore);
				response.setBody(drugstoreDTO);
				response.setResponseCode(201);
				
			} else if(DefaultMethods.UPDATE.equals(nameRequest)) {
				DrugstoreDTO drugstoreDTO = (DrugstoreDTO) request.getBody();
				Long idDrugtore = Long.valueOf(request.getPathVariables().get(0));
				Drugstore drugstore = drugstoreService.update(idDrugtore, drugstoreDTO);
				response.setBody(drugstoreConverter.toDto(drugstore));
				response.setResponseCode(200);
				
			} else if(DefaultMethods.DELETE.equals(nameRequest)) {
				Long idDrugstore = Long.valueOf(request.getPathVariables().get(0));
				drugstoreService.remove(idDrugstore);
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
