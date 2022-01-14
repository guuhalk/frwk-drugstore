package com.msorders.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.msschemas.dto.OrderDTO;

@Service
public class OrderProducerService {

	private static final Logger logger = LoggerFactory.getLogger(OrderProducerService.class);
	
	@Value("${topic.name}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, OrderDTO> kafkaTemplate;
	
	public void sendMessage(OrderDTO orderDTO) {
		kafkaTemplate.send(topic, orderDTO).addCallback(
			success -> logger.info("Message send " + success.getProducerRecord().value()),
			failure -> logger.error("Message failure " + failure.getMessage())
		);
	}
}
