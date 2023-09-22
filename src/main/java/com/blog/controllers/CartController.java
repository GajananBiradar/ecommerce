package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.CartDto;
import com.blog.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/{userId}/{productId}/{quantity}")
	private ResponseEntity<CartDto> addToCart(@PathVariable int userId, @PathVariable int productId,
			@PathVariable int quantity) {
		CartDto addToCart = this.cartService.addTocart(userId, productId, quantity);

		return new ResponseEntity<CartDto>(addToCart, HttpStatus.CREATED);
	}

	@GetMapping("/products")
	private ResponseEntity<List<CartDto>> getAllProductsInCart() {
		List<CartDto> allProductsInCart = this.cartService.getAllProductsInCart();

		if (allProductsInCart.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<List<CartDto>>(allProductsInCart, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	private ResponseEntity<List<CartDto>> getCartByUser(@PathVariable int userId) {
		List<CartDto> cartByUser = this.cartService.getCartByUser(userId);

		if (cartByUser.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(cartByUser, HttpStatus.OK);
	}

	@GetMapping("/product/{productId}")
	private ResponseEntity<List<CartDto>> getCartByProductId(@PathVariable int productId) {
		List<CartDto> cartsByProductId = this.cartService.getCartsByProductId(productId);

		if (cartsByProductId.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(cartsByProductId, HttpStatus.OK);
	}

	@GetMapping("/{cartId}")
	private ResponseEntity<CartDto> getCartByCartId(@PathVariable int cartId) {
		CartDto getCartByCartId = this.cartService.getCartByCartId(cartId);

		return new ResponseEntity<>(getCartByCartId, HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}/product/{productId}")
	private void deleteProductFromCart(@PathVariable int userId, @PathVariable int productId) {
		this.cartService.removeFromCart(userId, productId);
	}

	@PutMapping("/user/{userId}/product/{productId}/quantity/{quantity}")
	private ResponseEntity<CartDto> updateCartQunatity(@PathVariable int userId, @PathVariable int productId,
			@PathVariable int quantity) {
		CartDto updatedCart = this.cartService.updateCart(userId, productId, quantity);

		return new ResponseEntity<CartDto>(updatedCart, HttpStatus.CREATED);
	}

	@DeleteMapping("/buy/user/{userId}/product/{productId}")
	private void buyProduct(@PathVariable int userId, @PathVariable int productId) {
		this.cartService.buyProduct(userId, productId);
	}
	
}
