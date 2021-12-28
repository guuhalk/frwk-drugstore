package com.msorders.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msorders.model.Order;
import com.msschemas.dto.OrderDTO;

@Component
public class OrderConverter {

	@Autowired
	private ModelMapper modelMapper;
 	
	public List<OrderDTO> toCollectionDto(List<Order> orders) {
		return orders.stream()
				.map(order -> toDto(order))
				.collect(Collectors.toList());
	}
	
	public OrderDTO toDto(Order order) {
 		return modelMapper.map(order, OrderDTO.class);
 	}
	
	public Order toEntity(OrderDTO orderDTO) {
		return modelMapper.map(orderDTO, Order.class);
	}
	
	public void copyToEntity(OrderDTO orderDTO, Order order) {
		modelMapper.map(orderDTO, order);
	}
}
