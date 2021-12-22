package com.msusers.model;

import javax.persistence.Embeddable;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Embeddable
@Table(name = "tb_address")
public class Address {

	private String postalCode;
	
	private String street;
	
	private String number;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
}
