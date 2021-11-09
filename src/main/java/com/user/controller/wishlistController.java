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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.dto.WishlistDTO;
import com.user.repository.BuyerRepository;
import com.user.service.WishListService;
import com.user.service.cartService;

@RestController
@RequestMapping(value="/api")
public class wishlistController {
	@Autowired
	WishListService wishListService;
	@Autowired
	Environment environment;
	@Autowired
	BuyerRepository buyerRepository;
	@Autowired
	cartService CartService;
	@PostMapping(value="/{buyerid}/Wishlist/add")
	public ResponseEntity<String> addWishlist(@PathVariable String buyerid,@RequestBody String productid ) throws Exception{ 
		try
		{	wishListService.checkbuyerandproductExist(buyerid,productid);
			wishListService.checkProductExist(buyerid,productid);
			wishListService.addtowishlist(buyerid,productid);
			String result="successfully add product to the wishlist with productid:  "+productid;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);
		}}
	@GetMapping(value="/{buyerid}/Wishlist")
	public ResponseEntity<List<WishlistDTO>> wishlist(@PathVariable String buyerid ) throws Exception{ 
		try
		{
		List<WishlistDTO> list = wishListService.readWishList(buyerid);
		return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch (Exception exception) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
		}
	@DeleteMapping(value="/{buyerid}/Wishlist/cart")
	public ResponseEntity<String> wishlistDelete(@PathVariable String buyerid,@RequestBody  WishlistDTO  wdto) throws Exception{ 
		try
		{	
			wishListService.checkbuyerandproductExist(buyerid,wdto.getProdId());
			CartService.checkcartexist(buyerid,wdto.getProdId());
			wdto.setBuyerId(buyerid);
			wishListService.addtocart(wdto);
			String result="sucessfully add to cart with productId:  "+wdto.getProdId();
		return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch (Exception exception) {	
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
		}
}
