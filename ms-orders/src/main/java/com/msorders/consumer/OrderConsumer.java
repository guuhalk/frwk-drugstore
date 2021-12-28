package com.msorders.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.msorders.converter.OrderConverter;
import com.msorders.model.Order;
import com.msorders.service.OrderService;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.dto.OrderDTO;
import com.msschemas.exception.EntityInUseException;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Component
public class OrderConsumer {

	@Autowired
	private OrderConverter orderConverter;
	
	@Autowired
	private OrderService orderService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_REQUESTS)
	private Response consummer(Request request) {
		Response response = new Response();
		
		try {
			String nameRequest = request.getNameRequest();
			
			if(DefaultMethods.GET_ALL.equals(nameRequest)) {
				List<OrderDTO> ordersDTO = orderConverter.toCollectionDto(orderService.getAll());
				response.setBody(ordersDTO);
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.FIND_BY_ID.equals(nameRequest)) {
				Long idOrder = Long.valueOf(request.getPathVariables().get(0));
				response.setBody(orderConverter.toDto(orderService.findById(idOrder)));
				response.setResponseCode(HttpStatus.OK.value());
				
			} else if(DefaultMethods.CREATE.equals(nameRequest)) {
				OrderDTO orderDTO = (OrderDTO) request.getBody();
				Order order = orderService.create(orderConverter.toEntity(orderDTO));
				orderDTO = orderConverter.toDto(order);
				response.setBody(orderDTO);
				response.setResponseCode(HttpStatus.CREATED.value());
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
