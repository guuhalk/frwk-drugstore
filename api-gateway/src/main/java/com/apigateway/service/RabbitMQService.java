package com.apigateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Request;
import model.Response;

@Service
public class RabbitMQService {
	
	@Autowired
	private RabbitTemplate rabbitMQTemplate;
	
	public Response sendMenssage(String nameQueue, Request menssage) {
		return (Response) rabbitMQTemplate.convertSendAndReceive(nameQueue, menssage);
	}
	
	public Response sendMenssage(String nameQueue) {
		return (Response) rabbitMQTemplate.convertSendAndReceive(nameQueue);
	}
	
}
