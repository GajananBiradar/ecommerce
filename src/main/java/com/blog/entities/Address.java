package com.blog.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int addressId;
	
	@NotNull(message = "City cannot be null")
	private String city;
	
	@NotNull(message = "Pincode cannot be null")
	private int pincode;
	
	@NotNull(message = "Country cannot be null")
	private String country;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	private User user;
	
	
	
}
