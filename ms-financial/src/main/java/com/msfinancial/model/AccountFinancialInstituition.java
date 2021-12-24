package com.msfinancial.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_account_financial_instituition")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class AccountFinancialInstituition {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String account;
	
	@NotBlank
	private String agency;
	
	@NotNull
	private Long clientId;
	
	@ManyToOne
	@NotNull
	private FinancialInstituition financialInstituition;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp")
	private OffsetDateTime createdAt;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "timestamp")
	private OffsetDateTime updatedAt;

}
