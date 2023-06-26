package com.blog.services;

import java.util.List;

import com.blog.entities.Product;
import com.blog.payloads.ProductDto;

public interface ProductService {

	
	//Add Product
	ProductDto addProduct(ProductDto product, int sellerId);
	
	//Delete Product
	void deleteProduct(int productId);
	
	//Update Product
	ProductDto updateProduct(ProductDto product, int productId);
	
	//To get all Products
	List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//To get product by id
	ProductDto getProductById(int productId);
	
	//Get product by Seller
	List<ProductDto> getProductBySeller(int sellerId);
	
	//Reduce product quantity
	ProductDto reduceProductQuantity(int productId, int quantity);
	
	//Update the product status
	ProductDto updateProductStatus(int productId, String productStatus);
	
	
}
