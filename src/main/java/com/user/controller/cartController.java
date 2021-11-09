package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.dto.CartDTO;
import com.user.service.cartService;

@RestController
@RequestMapping(value="/api")
public class cartController {
	@Autowired
	cartService CartService;
	@Autowired
	Environment environment;
	@GetMapping(value="/{buyerId}/cart")
	public ResponseEntity<List<CartDTO>> CartList(@PathVariable String buyerId) throws Exception
	{
		try
		{
			List<CartDTO> cartlist=CartService.listCartItems(buyerId); 
			return new ResponseEntity<>(cartlist, HttpStatus.OK);
		}
		catch(Exception exception) {
			String message="no product added in the cart ";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,message);
		}
	}
	@PutMapping(value="/{buyerId}/cart")
	public ResponseEntity<String> UpdateCart(@PathVariable String buyerId,@RequestBody CartDTO cartDTO) throws Exception
	{
		try
		{	cartDTO.setBuyerId(buyerId);
			CartService.updateCartItem(cartDTO);
			String result = "Sucessfully updated with productId"+cartDTO.getProdId();
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@DeleteMapping(value="/{buyerId}/cart/deleteAll")
	public ResponseEntity<String> DeleteAll(@PathVariable String buyerId) throws Exception
	{
		try
		{
			CartService.deleteAllbyuserid(buyerId);
			String result = "Sucessfully deleted all products in cart with buyerId "+buyerId;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	
		catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@DeleteMapping(value="/{buyerId}/cart/{prodId}/delete")
	public ResponseEntity<String> Delete(@PathVariable String buyerId,@PathVariable String prodId) throws Exception
	{
		try
		{
			CartService.deleteCartItem(buyerId,prodId);
			String result = "Product Removed from The cart Of Buyer having  buyerId Has  "+buyerId;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	
		catch(Exception exception) {
			String message=exception.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, message, exception);
		}
	}
	
	@PostMapping(value="/{buyerId}/cart/order/kafka")
	public ResponseEntity<String> addCart(@PathVariable String buyerId,@RequestBody CartDTO cartDTO) throws Exception
	{
		try
		{	cartDTO.setBuyerId(buyerId);
			CartService.addToCart(cartDTO);
			String result = "Sucessfully added to the cart";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception exception) {
			String message=exception.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,message, exception);
		}
	}
	
	@PostMapping(value="/{buyerId}/cart/add")
	public ResponseEntity<String> addCartNormally(@PathVariable String buyerId,@RequestBody CartDTO cartDTO) throws Exception
	{
		try
		{	cartDTO.setBuyerId(buyerId);
			CartService.addToCartNormally(cartDTO);
			String result = "Product Added To Cart Sucessfully";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception exception) {
			String message=exception.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,message, exception);
		}
	}
	
	
	
	
	
	
	
	
	
	

}

