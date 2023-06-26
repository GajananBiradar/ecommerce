package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Address;
import com.blog.entities.User;

public interface AddressRepo extends JpaRepository<Address, Integer>{

	List<Address> findByCity(String city);
	
	
}
