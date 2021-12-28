package com.msorders.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msorders.model.Order;
import com.msorders.model.OrderItem;
import com.msorders.repository.OrderRepository;
import com.msschemas.constants.DefaultMethods;
import com.msschemas.constants.RabbitMQConstants;
import com.msschemas.constants.UserMethods;
import com.msschemas.dto.DrugstoreDTO;
import com.msschemas.dto.ProductDTO;
import com.msschemas.dto.UserDTO;
import com.msschemas.exception.EntityNotFoundException;
import com.msschemas.exception.GenericException;
import com.msschemas.model.Request;
import com.msschemas.model.Response;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	private Map<String, Long> productsQuantityInInventory;
	
	@Autowired
	private RabbitMqService mqService;
	
	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long idOrder) {
		return getOrThrowException(idOrder);
	}
	
	public Order getOrThrowException(Long idOrder) {
		return orderRepository.findById(idOrder).orElseThrow(() -> new EntityNotFoundException("Order not found!"));
	}
	
	@Transactional
	public Order create(Order order) {
		setProductFromItems(order.getOrderItems());
		checkQuantityProductsInInventory(order.getOrderItems());
		calculateTotalPricePerItem(order.getOrderItems());
		calculateTotalOrderValue(order);
		
		setOrderDrugstore(order);
		setOrderClient(order);
		
		order = orderRepository.save(order);
		
		updateInventoryQuantityFromProducts();
		
		//TODO: Avisar equipe do C# sobre a compra
		
		return order;
	}
	
	private void setProductFromItems(List<OrderItem> orderItems) {
		orderItems.forEach(item -> {
			Response response = mqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, 
								new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(item.getProductId())));
			
			if(!response.getResponseCode().equals(200)) {
				throw new EntityNotFoundException("Product not found");
			}
			
			ProductDTO productDTO = (ProductDTO) response.getBody();
			item.setProductDTO(productDTO);
		});
	}
	
	private void checkQuantityProductsInInventory(List<OrderItem> orderItems) {
		productsQuantityInInventory = new HashMap<>();
		
		orderItems.forEach(item -> {
			if(!productsQuantityInInventory.containsKey(item.getProductDTO().getId())) {
				productsQuantityInInventory.put(item.getProductDTO().getId(), item.getProductDTO().getInventoryQuantity());
			}
		});
		
		orderItems.forEach(item -> {
			Long newInventoryQuantity = productsQuantityInInventory.get(item.getProductDTO().getId()) - item.getQuantity();
			
			if(newInventoryQuantity < 0) {
				throw new GenericException("Don't have enough of product " + item.getProductDTO().getName() + " in inventory.");
			}
			
			productsQuantityInInventory.put(item.getProductDTO().getId(), newInventoryQuantity);
		});
	}
	
	private void calculateTotalPricePerItem(List<OrderItem> orderItems) {
		if(orderItems == null || orderItems.isEmpty()) return;
		
		orderItems.forEach(item -> {
			BigDecimal unitPrice = item.getUnitPrice() != null 
							? item.getUnitPrice() : item.getProductDTO().getPrice();
			item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
		});
	}
	
	private void calculateTotalOrderValue(Order order) {
		Function<OrderItem, BigDecimal> totalMapper = OrderItem -> OrderItem.getTotalPrice();
		BigDecimal totalValue = order.getOrderItems()
					.stream()
					.map(totalMapper)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		order.setTotalValue(totalValue);
	}
	
	private void setOrderDrugstore(Order order) {
		Response response = mqService.sendMenssage(RabbitMQConstants.QUEUE_PHARMACIES, 
						new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(order.getDrugstoreId())));

		if(!response.getResponseCode().equals(200)) {
			throw new EntityNotFoundException("Drugstore not found");
		}
		
		DrugstoreDTO drugstoreDTO = (DrugstoreDTO) response.getBody();
		order.setDrugstoreId(drugstoreDTO.getId());
	}
	
	private void setOrderClient(Order order) {
		if(order.getCpfClient() != null && order.getClientId() == null) {
			Response response = mqService.sendMenssage(RabbitMQConstants.QUEUE_USER, 
					new Request(UserMethods.FIND_BY_CPF, Arrays.asList(order.getCpfClient())));
			
			if(response.getResponseCode().equals(200)) {
				UserDTO userDTO = (UserDTO) response.getBody();
				order.setClientId(userDTO.getId());
			}
		}
	}
	
	private void updateInventoryQuantityFromProducts() {
		for(Map.Entry<String, Long> productMap : productsQuantityInInventory.entrySet()) {
			Response response = mqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, 
							new Request(DefaultMethods.FIND_BY_ID, Arrays.asList(productMap.getKey())));
			
			ProductDTO productDTO = (ProductDTO) response.getBody();
			
			productDTO.setInventoryQuantity(productMap.getValue());
			mqService.sendMenssage(RabbitMQConstants.QUEUE_PRODUCTS, 
							new Request(DefaultMethods.UPDATE, productDTO, Arrays.asList(productDTO.getId())));
		}
	}

}
