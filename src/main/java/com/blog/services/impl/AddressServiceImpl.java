package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Address;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.AddressDto;
import com.blog.repositories.AddressRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Override
	public AddressDto getAddressById(Integer addressId) {
		Address address = this.addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

		return this.modelMapper.map(address, AddressDto.class);
	}

	@Override
	public List<AddressDto> getAllAddress() {
		List<Address> allAddress = this.addressRepo.findAll();
		List<AddressDto> addresses = allAddress.stream()
				.map((address) -> this.modelMapper.map(address, AddressDto.class)).collect(Collectors.toList());
		return addresses;
	}

	@Override
	public List<AddressDto> getAllAddressByCity(String city) {
		List<Address> findByCity = this.addressRepo.findByCity(city);
		
		List<AddressDto> addressesByCity= findByCity.stream().map((cit) -> this.modelMapper.map(cit, AddressDto.class)).collect(Collectors.toList()); 
	
		return addressesByCity;
	}

	@Override
	public AddressDto createAddress(AddressDto addressDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Address address = this.modelMapper.map(addressDto, Address.class);
		address.setUser(user);

		Address createdAddress = this.addressRepo.save(address);
		return this.modelMapper.map(createdAddress, AddressDto.class);
	}

	@Override
	public AddressDto updateAddress(AddressDto addressDto, Integer addressId) {
		Address address = this.addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

		
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setPincode(addressDto.getPincode());
		
		Address updatedAddress = this.addressRepo.save(address);
		
		return this.modelMapper.map(updatedAddress, AddressDto.class);
	}

	@Override
	public void deleteAddress(Integer addressId) {
		Address address = this.addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

		this.addressRepo.delete(address);
	}

}
