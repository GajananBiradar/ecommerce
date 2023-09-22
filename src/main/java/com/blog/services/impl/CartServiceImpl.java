package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Cart;
import com.blog.entities.Product;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.exceptions.UserAccessDeniedException;
import com.blog.payloads.CartDto;
import com.blog.payloads.ProductDto;
import com.blog.repositories.CartRepo;
import com.blog.repositories.ProductRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Override
	public CartDto addTocart(Integer userId, Integer productId, Integer quantity) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		Cart cart = this.cartRepo.findByProduct_IdAndUser_Id(productId, userId);

		if (cart != null && cart.getUser() == user) {
			if (cart.getRequiredQuantity() + quantity > product.getProduct_quantity()) {
				throw new UserAccessDeniedException(
						"Sorry this much quantity is not vaialbale, the maximum quantity you can add in your cart should be less than "
								+ product.getProduct_quantity());
			}
			cart.setRequiredQuantity(cart.getRequiredQuantity() + quantity);
			cart.setTotalPrice(cart.getRequiredQuantity() * product.getPrice());

		} else {
			cart = new Cart();
			cart.setUser(user);
			cart.setProduct(product);
			cart.setProduct_name(product.getProduct_name());
			cart.setItemPrice(product.getPrice());
			cart.setProductStatus(product.getProductStatus());
			cart.setRequiredQuantity(quantity);
			cart.setTotalPrice(quantity * product.getPrice());
		}

		Cart savedCart = this.cartRepo.save(cart);

		return this.modelMapper.map(savedCart, CartDto.class);
	}

	@Override
	public void removeFromCart(Integer userId, Integer productId) {
		Cart cart = this.cartRepo.findByProduct_IdAndUser_Id(productId, userId);

		this.cartRepo.delete(cart);
	}

	@Override
	public CartDto updateCart(Integer userId, Integer productId, Integer quantity) {
		Cart cart = this.cartRepo.findByProduct_IdAndUser_Id(productId, userId);

		cart.setRequiredQuantity(quantity);
		cart.setTotalPrice(quantity * cart.getItemPrice());

		Cart updatedCart = this.cartRepo.save(cart);
		return this.modelMapper.map(updatedCart, CartDto.class);
	}

	@Override
	public List<CartDto> getAllProductsInCart() {
		List<Cart> allProductInCart = this.cartRepo.findAll();
		List<CartDto> allProducts = allProductInCart.stream()
				.map((product) -> this.modelMapper.map(product, CartDto.class)).collect(Collectors.toList());

		return allProducts;
	}

	@Override
	public List<CartDto> getCartByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		List<Cart> cartOfUser = this.cartRepo.findByUser(user);
		List<CartDto> userCart = cartOfUser.stream().map((cart) -> this.modelMapper.map(cart, CartDto.class))
				.collect(Collectors.toList());

		return userCart;
	}

	@Override
	public List<CartDto> getCartsByProductId(Integer productId) {
		List<Cart> cartsByProduct_Id = this.cartRepo.findByProduct_Id(productId);
		List<CartDto> cartsByProduct = cartsByProduct_Id.stream()
				.map((cart) -> this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());

		return cartsByProduct;
	}

	@Override
	public CartDto getCartByCartId(int cartId) {
		Cart cart = this.cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

		return this.modelMapper.map(cart, CartDto.class);
	}

	@Override
	public void buyProduct(Integer userId, Integer productId) {
		Cart cart = this.cartRepo.findByProduct_IdAndUser_Id(productId, userId);

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		if (product.getProductStatus() == AppConstants.PRODUCT_STATUS_OUTOFSTOCK
				|| product.getProduct_quantity() == 0) {
			throw new UserAccessDeniedException("The product is currently unavilable please try after some time");
		}

		int remainingQunatity = product.getProduct_quantity() - cart.getRequiredQuantity();
		if (remainingQunatity == 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_OUTOFSTOCK);
		} else if (remainingQunatity > 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_AVAILABLE);
		}

		product.setProduct_quantity(remainingQunatity);

		this.productRepo.save(product);

		this.cartRepo.delete(cart);
	}

}
