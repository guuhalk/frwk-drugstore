package com.apigateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Request;


@Service
public class RabbitMQService {
	
	@Autowired
	private RabbitTemplate rabbitMQTemplate;
	
	public Object sendMenssage(String nameQueue, Request menssage) {
		return rabbitMQTemplate.convertSendAndReceive(nameQueue, menssage);
	}
	
	public Object sendMenssage(String nameQueue) {
		return rabbitMQTemplate.convertSendAndReceive(nameQueue);
	}
	
}
