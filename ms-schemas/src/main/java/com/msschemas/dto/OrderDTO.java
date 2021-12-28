package com.msschemas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.msschemas.model.enumeration.PaymentMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long drugstoreId;
	
	@NotEmpty
	private List<OrderItemDTO> orderItems;
	
	private Long clientId;
	
	@CPF
	private String cpfClient;
	
	private BigDecimal totalValue;
	
	private PaymentMethod paymentMethod;
	
	private OffsetDateTime createdAt;
	
	private OffsetDateTime updatedAt;
}
