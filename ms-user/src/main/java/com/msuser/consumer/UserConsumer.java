package com.msuser.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import constants.RabbitMQConstants;
import dto.UserDTO;

@Component
public class UserConsumer {

	@RabbitListener(queues = RabbitMQConstants.QUEUE_USER)
	private void consummer(UserDTO userDTO) {
		System.out.println("===========================");
		System.out.println("Request");
		System.out.println("Nome:".concat(userDTO.getName()));
		System.out.println("===========================");
	}
}
