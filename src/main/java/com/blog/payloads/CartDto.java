package com.blog.payloads;

import com.blog.entities.Product;
import com.blog.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class CartDto {

	private int id;
	
	private String product_name;
	
	private int requiredQuantity;	

	private Double totalPrice;
	
	private String productStatus;
	
	private int userId;
	
	
}
