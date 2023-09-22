package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private int id;

	@NotNull(message = "Email cannot be null")
	private String product_name;

	@NotNull(message = "Product quantity cannot be null")
	private int product_quantity;

	@NotNull(message = "Product price cannot be null")
	private Double price;

	@NotNull(message = "Product status cannot be null")
	private String productStatus;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "seller_id")
	private User user;

}
