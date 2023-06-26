package com.blog.services;

import java.util.List;

import com.blog.payloads.AddressDto;

public interface AddressService {

	//Get address by Id
	AddressDto getAddressById(Integer addressId);
	
	//Get all address
	List<AddressDto> getAllAddress();
	
	//Get all address by City
	List<AddressDto> getAllAddressByCity(String city);
	
	//Create address
	AddressDto createAddress(AddressDto addressDto, Integer userId);
	
	//Update Address
	AddressDto updateAddress(AddressDto addressDto, Integer userId);
	
	//Delete Address
	void deleteAddress(Integer addressId);
	
}
