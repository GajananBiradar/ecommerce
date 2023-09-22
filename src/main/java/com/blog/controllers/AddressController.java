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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.AddressDto;
import com.blog.services.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@GetMapping("/{addressId}")
	private ResponseEntity<AddressDto> getAddressById(@PathVariable("addressId") int addressId) {
		AddressDto address = this.addressService.getAddressById(addressId);

		return new ResponseEntity<AddressDto>(address, HttpStatus.OK);
	}

	@GetMapping("/")
	private ResponseEntity<List<AddressDto>> getAllAddresses() {
		List<AddressDto> allAddress = this.addressService.getAllAddress();

		if (allAddress.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(allAddress, HttpStatus.OK);
	}

	@GetMapping("/city/{city}")
	private ResponseEntity<List<AddressDto>> getAddressByCity(@PathVariable("city") String city) {
		List<AddressDto> allAddressByCity = this.addressService.getAllAddressByCity(city);

		if (allAddressByCity.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return new ResponseEntity<>(allAddressByCity, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	private ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto,
			@PathVariable("userId") int userId) {
		AddressDto createdAddress = this.addressService.createAddress(addressDto, userId);

		return new ResponseEntity<AddressDto>(createdAddress, HttpStatus.CREATED);
	}

	@PutMapping("/{addressId}")
	private ResponseEntity<AddressDto> updateAddress(@PathVariable("addressId") int addressId,
			@RequestBody AddressDto addressDto) {
		AddressDto updateAddress = this.addressService.updateAddress(addressDto, addressId);

		return new ResponseEntity<AddressDto>(updateAddress, HttpStatus.CREATED);
	}

	@DeleteMapping("/{addressId}")
	private ResponseEntity<Void> deleteAddress(@PathVariable int addressId) {
		this.addressService.deleteAddress(addressId);
		
		return ResponseEntity.ok().build();
	}

}
