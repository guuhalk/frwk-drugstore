package com.msuser.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;
import dto.UserDTO;

@Component
public class UserConsumer {

	@Autowired
	private RabbitTemplate rabbitMQTemplate;
	
	
	public void sendMenssage(String nameQueue, Object menssage) {
		this.rabbitMQTemplate.convertAndSend(nameQueue, menssage);
	}
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_USER)
	private UserDTO consummer(UserDTO userDTO) throws IOException, InterruptedException, TimeoutException {
		System.out.println("===========================");
		System.out.println("Request");
		System.out.println("Nome:".concat(userDTO.getName()));
		System.out.println("===========================");
		
		return userDTO;
		
	}
}
