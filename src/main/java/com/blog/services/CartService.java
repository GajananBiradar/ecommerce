package com.blog.services;

import java.util.List;

import com.blog.payloads.CartDto;
import com.blog.payloads.ProductDto;

public interface CartService {

	//Add to cart
	CartDto addTocart(Integer productId, Integer userId, Integer quantity);
	
	//Remove from cart
	void removeFromCart(Integer userId, Integer productId);
	
	//Update Cart 
	CartDto updateCart(Integer userId, Integer productId, Integer quantity);
	
	//Get all products
	List<CartDto> getAllProductsInCart();
	
	//get products by user
	List<CartDto> getCartByUser(Integer userId);
	
	//get products by productId
	List<CartDto> getCartsByProductId(Integer productId);
	
	//Get products by cart id
	CartDto getCartByCartId(int cartId);
	
	//Buy the product
	void buyProduct(Integer userId, Integer productId);
}
