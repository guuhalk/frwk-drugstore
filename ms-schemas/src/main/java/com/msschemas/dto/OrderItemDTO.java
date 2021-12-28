package com.msschemas.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private BigDecimal unitPrice;
	
	private BigDecimal totalPrice;
	
	@NotNull
	private Integer quantity;
	
	private String observation;
	
	@NotNull
	private Long productId;
	
	private OrderDTO orderimpl;
}
