package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Product;
import com.blog.entities.User;

public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findByUser_Id(int seller_id);
	
	List<Product> findByUser(User user);
	
	
}
