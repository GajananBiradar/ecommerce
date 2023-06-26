package com.blog.services.impl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Product;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.exceptions.UserAccessDeniedException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.ProductDto;
import com.blog.repositories.ProductRepo;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.ProductService;

@Service
@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> allProduct = this.productRepo.findAll(p);

		List<Product> allProducts = allProduct.getContent();
		List<ProductDto> products = allProducts.stream()
				.map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

		return products;
	}

	@Override
	public ProductDto getProductById(int productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductBySeller(int sellerId) {
		List<Product> productBySellerId = this.productRepo.findByUser_Id(sellerId);
		List<ProductDto> productBySeller = productBySellerId.stream()
				.map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

		return productBySeller;
	}

	@Override
	public ProductDto addProduct(ProductDto productDto, int sellerId) {
		User seller = this.userRepo.findById(sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller", "id", sellerId));

		Role sellerUserRole = this.roleRepo.findById(AppConstants.SELLER_USER)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.SELLER_USER));

		if (!seller.getRoles().contains(sellerUserRole)) {
			throw new UserAccessDeniedException("The user doesn't have access to add products.");
		}

		Product product = this.modelMapper.map(productDto, Product.class);
		product.setUser(seller);

		Product productCreated = this.productRepo.save(product);

		return this.modelMapper.map(productCreated, ProductDto.class);
	}

	@Override
	public void deleteProduct(int productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		this.productRepo.delete(product);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, int productId) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		product.setPrice(productDto.getPrice());
		product.setProduct_name(productDto.getProduct_name());
		product.setProduct_quantity(productDto.getProduct_quantity());
		if (productDto.getProduct_quantity() == 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_OUTOFSTOCK);
		} else if (productDto.getProduct_quantity() > 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_AVAILABLE);
		}

		Product updatedProduct = this.productRepo.save(product);
		return this.modelMapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public ProductDto reduceProductQuantity(int productId, int quantity) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		product.setProduct_quantity(quantity);
		if (quantity == 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_OUTOFSTOCK);
		} else if (quantity > 0) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_AVAILABLE);
		}

		Product quantityUpdated = this.productRepo.save(product);
		return this.modelMapper.map(quantityUpdated, ProductDto.class);
	}

	@Override
	public ProductDto updateProductStatus(int productId, String productStatus) {
		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		if (productStatus.equalsIgnoreCase("available")) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_AVAILABLE);
		} else if (productStatus.equalsIgnoreCase("unavailable")) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_UNAVAILABLE);
		} else if (productStatus.equalsIgnoreCase("outofstock")) {
			product.setProductStatus(AppConstants.PRODUCT_STATUS_OUTOFSTOCK);
		}

		Product productStatusUpdated = this.productRepo.save(product);

		return this.modelMapper.map(productStatusUpdated, ProductDto.class);
	}

}
