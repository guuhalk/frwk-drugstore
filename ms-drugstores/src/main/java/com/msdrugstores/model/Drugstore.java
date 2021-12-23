package com.msdrugstores.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_drugstore")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Drugstore {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@CNPJ
	@NotBlank
	private String cnpj;

	@Email
	private String email;
	
	@ElementCollection
	@CollectionTable(name = "drugstore_phones", joinColumns = @JoinColumn(name = "drugstore_id"))
	private List<String> phones = new ArrayList<>();
	
	@Embedded
	private Address address;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp")
	private OffsetDateTime createdAt;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "timestamp")
	private OffsetDateTime updatedAt;

}
