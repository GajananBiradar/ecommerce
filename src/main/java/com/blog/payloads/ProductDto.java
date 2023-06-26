package com.blog.payloads;

import com.blog.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductDto {

	private int id;

	private String product_name;

	private Double price;

	private int product_quantity;

	private String productStatus;

	@JsonProperty("sellerId")
	private int userId;
	
}
