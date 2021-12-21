package com.apigateway.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
	
	@Autowired
	private RabbitTemplate rabbitMQTemplate;
	
	
	public Object sendMenssage(String nameQueue, Object menssage) {
		return rabbitMQTemplate.convertSendAndReceive(nameQueue, menssage);
	}
	
	public Object sendMenssage(String nameQueue) {
		return rabbitMQTemplate.convertSendAndReceive(nameQueue);
	}
	
	public List<Object> sendListMenssage(String nameQueue) {
		return (List<Object>) rabbitMQTemplate.convertSendAndReceive(nameQueue);
	}

}
