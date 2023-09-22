package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Cart;
import com.blog.entities.Product;
import com.blog.entities.User;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	List<Cart> findByUser(User user);
	
	List<Cart> findByProduct_Id(Integer productId);
	
	Cart findByProduct_IdAndUser_Id(Integer productId, Integer userId);
		
}
