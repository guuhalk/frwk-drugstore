package com.msusers.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msuser.service.UserService;

import constants.RabbitMQConstants;
import model.Request;
import model.Response;

@Component
public class UserConsumer {

	@Autowired
	private UserService userService;
	
	@RabbitListener(queues = RabbitMQConstants.QUEUE_USER)
	private Object consummer(Request request) throws IOException, InterruptedException, TimeoutException {

		Response response = new Response();
		
		switch (request.getNameRequest()) {
		case "getAll":
			
			break;
		
<<<<<<< HEAD:ms-users/src/main/java/com/msusers/consumer/UserConsumer.java
		return userDTO;
=======
		case "alterUser":
			
			break;
		case "saveUser":
			
			break;
			

		default:
			break;
		}
		
		return response;

>>>>>>> f6cffbfe678da59c74e31781187aa9ae1e7547d5:ms-user/src/main/java/com/msuser/consumer/UserConsumer.java
	}
}
