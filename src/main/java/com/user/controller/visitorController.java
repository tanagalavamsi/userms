package com.user.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.dto.productDTO;
import com.user.feign.productMsfeign;

@RestController
@RequestMapping(value="/api")
public class visitorController {
	@Autowired
	productMsfeign productFeign;
	@GetMapping(value = "/product/category/{category}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> findCategoryproducts(@PathVariable String category) throws Exception{
		try {
			List<productDTO> productList = productFeign.findproductbycategory(category);		
			return new ResponseEntity<>(productList, HttpStatus.OK);
		} 
		catch (Exception exception) {
			String message="no products available for this category ";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, message, exception);
		}
	}
	@GetMapping(value = "/product/{category}/{subCategory}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> findSubCategoryProducts(@PathVariable String category,@PathVariable String subCategory) throws Exception{
		try {
			List<productDTO> productList = productFeign.getProductbySubCategory(category,subCategory);
			return new ResponseEntity<>(productList, HttpStatus.OK);
		} 
		catch (Exception exception) {
			String message="no products available for this category ";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,message, exception);
		}
	}
	@GetMapping(value = "/product/{productName}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> findSubCategoryProducts(@PathVariable String productName) throws Exception{
		try {
	
			List<productDTO> productList = productFeign.findProductByName(productName);
			
			return new ResponseEntity<>(productList, HttpStatus.OK);
		} 
		catch (Exception exception) {
			
			String message="no product available in the app with this name";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,message, exception);
		}
	}
	
}
