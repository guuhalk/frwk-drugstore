package com.msusers.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import com.msusers.model.enums.UserType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter 
@Setter
@Entity
@Table(name = "tb_user")
public class User {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@CPF
	@NotBlank
	private String cpf;
	
	private LocalDate birthday;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "userType")
	private UserType userType;
	
	@Embedded
	private Address address;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp", name = "createdAt")
	private OffsetDateTime createdAt;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "timestamp", name = "updatedAt")
	private OffsetDateTime updatedAt;

	public boolean isNew() {
		return id == null;
	}
	
}