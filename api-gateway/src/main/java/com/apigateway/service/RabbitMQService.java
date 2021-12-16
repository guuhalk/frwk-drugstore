package com.apigateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
	
	@Autowired
	private RabbitTemplate rabbitMQTemplate;
	
	public void sendMenssage(String nameQueue, Object menssage) {
		
	}

}
