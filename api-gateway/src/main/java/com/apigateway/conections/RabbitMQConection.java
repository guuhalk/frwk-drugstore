package com.apigateway.conections;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apigateway.constants.RabbitMQConstants;


@Component
public class RabbitMQConection {
	
	private static final String NAME_EXCHANGE = "amq.direct";
	
	@Autowired
	private AmqpAdmin amqpAdmin; 
	
	private Queue queue(String nameQueue) {
		return new Queue(nameQueue, true, false, false);
	}
	
	private DirectExchange directExchange() {
		return new DirectExchange(NAME_EXCHANGE);
	}
	
	private Binding relationship(Queue queue, DirectExchange exchange )  {
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
	}
	
	@PostConstruct
	private void add() {
		Queue queueUser = this.queue(RabbitMQConstants.QUEUE_USER);
		Queue queueCategory = this.queue(RabbitMQConstants.QUEUE_CATEGORY);
		Queue queuePharmacies = this.queue(RabbitMQConstants.QUEUE_PHARMACIES);
		Queue queueFinancialInstitutions = this.queue(RabbitMQConstants.QUEUE_FINANCIAL_INSTITUTIONS);
		Queue queueRequests = this.queue(RabbitMQConstants.QUEUE_REQUESTS);
		Queue queueProducts = this.queue(RabbitMQConstants.QUEUE_PRODUCTS);
		Queue queueAccounts = this.queue(RabbitMQConstants.QUEUE_ACCOUNTS);
		
		DirectExchange exchange = this.directExchange();
		
		Binding linkUser = this.relationship(queueUser, exchange);
		Binding linkCategory = this.relationship(queueCategory, exchange);
		Binding linkPharmacies = this.relationship(queuePharmacies, exchange);
		Binding linkFinancialInstitutions = this.relationship(queueFinancialInstitutions, exchange);
		Binding linkRequests = this.relationship(queueRequests, exchange);
		Binding linkProducts = this.relationship(queueProducts, exchange);
		Binding linkAccounts = this.relationship(queueAccounts, exchange);
		
		
		amqpAdmin.declareQueue(queueUser);
		amqpAdmin.declareQueue(queueCategory);
		amqpAdmin.declareQueue(queuePharmacies);
		amqpAdmin.declareQueue(queueFinancialInstitutions);
		amqpAdmin.declareQueue(queueRequests);
		amqpAdmin.declareQueue(queueProducts);
		amqpAdmin.declareQueue(queueAccounts);

		amqpAdmin.declareExchange(exchange);
		
		amqpAdmin.declareBinding(linkUser);
		amqpAdmin.declareBinding(linkCategory);
		amqpAdmin.declareBinding(linkPharmacies);
		amqpAdmin.declareBinding(linkFinancialInstitutions);
		amqpAdmin.declareBinding(linkRequests);
		amqpAdmin.declareBinding(linkProducts);
		amqpAdmin.declareBinding(linkAccounts);
		
	}
}
