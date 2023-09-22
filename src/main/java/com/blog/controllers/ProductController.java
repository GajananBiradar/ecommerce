package com.blog.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.AppConstants;
import com.blog.payloads.ProductDto;
import com.blog.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	private ResponseEntity<List<ProductDto>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		List<ProductDto> allProducts = this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);

		if (allProducts.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	private ResponseEntity<ProductDto> getProductById(@PathVariable int productId) {
		ProductDto productById = this.productService.getProductById(productId);

		return new ResponseEntity<>(productById, HttpStatus.OK);
	}

	@GetMapping("/seller/{sellerId}")
	private ResponseEntity<List<ProductDto>> getProductBySellerId(@PathVariable int sellerId) {
		List<ProductDto> productById = this.productService.getProductBySeller(sellerId);

		return new ResponseEntity<>(productById, HttpStatus.OK);
	}
	
//	@PreAuthorize("hasRole('SCOPE_SELLER')")
	@PostMapping("/{sellerId}")
	private ResponseEntity<ProductDto> addProduct(@PathVariable int sellerId, @RequestBody ProductDto productDto) {
		ProductDto addProduct = this.productService.addProduct(productDto, sellerId);

		return new ResponseEntity<ProductDto>(addProduct, HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}")
	private void deleteProduct(@PathVariable int productId) {
		this.productService.deleteProduct(productId);
	}

	@PutMapping("/{productId}")
	private ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto productDto) {
		ProductDto updatedProduct = this.productService.updateProduct(productDto, productId);

		return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{productId}/{quantity}")
	private ResponseEntity<ProductDto> changeProductQuantity(@PathVariable int productId, @PathVariable int quantity) {
		ProductDto changeProductQuantity = this.productService.reduceProductQuantity(productId, quantity);

		return new ResponseEntity<ProductDto>(changeProductQuantity, HttpStatus.OK);
	}

	@PutMapping("/{productId}/{productStatus}")
	private ResponseEntity<ProductDto> updateProductStatus(@PathVariable int productId,
			@PathVariable String productStatus) {
		ProductDto updateProductStatus = this.productService.updateProductStatus(productId, productStatus);

		return new ResponseEntity<ProductDto>(updateProductStatus, HttpStatus.OK);
	}

}
