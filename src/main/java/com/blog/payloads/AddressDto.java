package com.blog.payloads;

import com.blog.entities.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressDto {

	private int addressId;
	
	private int userId;
	
	private String city;	
	
	private String country;
	
	@Pattern(regexp="[0-9]{6}", message = "Only Valid for 6 digit indian pincode")
	private  int pincode;
}
