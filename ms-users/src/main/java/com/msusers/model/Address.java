package com.msusers.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Embeddable
public class Address {

	@Column(name = "postalCode", columnDefinition = "postalCode", table = "tb_user")
	private String postalCode;
	
	private String street;
	
	private String number;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
}
