package com.user.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.user.dto.LoginDTO;
import com.user.dto.SellerDTO;
import com.user.dto.productDTO;
import com.user.dto.quantityDTO;
import com.user.feign.productMsfeign;
import com.user.repository.SellerRepository;
import com.user.service.sellerService;

@RestController
@RequestMapping(value="/api")
public class sellerController {
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	productMsfeign productMsfeign;
	@Autowired
	sellerService SellerService;
	@PostMapping(value="/seller/{sellerId}/product")
	public ResponseEntity<String> addproduct (@PathVariable String sellerId, @Valid @RequestBody productDTO ProductDTO) throws Exception{ 
		try
		{	SellerService.AddProudct(ProductDTO,sellerId);
			String result ="successfully add the product";
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);
		}
	}
	@DeleteMapping(value = "/seller/{sellerId}/product/{prodId}")
	public ResponseEntity<String> deleteProductbyid(@PathVariable String sellerId,@PathVariable String prodId) throws Exception {
		try 
		{	SellerService.DeleteProduct(sellerId, prodId);
			String result="deleted successfully with productID"+prodId;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception exception)
		{ 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);
		}
	}
	@PutMapping(value = "/seller/{sellerId}/product")
	public ResponseEntity<String> updateProductbyid(@PathVariable String sellerId,@RequestBody  quantityDTO QuantityDTO) throws Exception {
		try 
		{	
			SellerService.updateProductbyid(sellerId,QuantityDTO);
			String result="successfully updated the Quantity: "+QuantityDTO.getQuantity()+"of the productID:"+QuantityDTO.getProdId();
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception exception)
		{ 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);
		}
	}
	@GetMapping(value = "/seller/{sellerId}/product/{prodId}")
	public ResponseEntity<productDTO> getProduct(@PathVariable String sellerId,@PathVariable String prodId) throws Exception {
		try 
		{	productDTO Product=SellerService.getproduct(sellerId,prodId);
			return new ResponseEntity<>(Product, HttpStatus.OK);
		}
		catch(Exception exception)
		{ 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,exception.getMessage(),exception);
		}
	}
	@GetMapping(value = "/sellers/{sellerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SellerDTO> findSeller(@PathVariable String sellerId) throws Exception{
		try {
			SellerDTO sellerDTO= SellerService.getsellerDetails(sellerId);
			
			return new ResponseEntity<>(sellerDTO, HttpStatus.OK);
		} 
		catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}
	@PostMapping(value = "/sellers/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loginbuyer(@RequestBody LoginDTO logindto) throws Exception{
		try {
			
			SellerService.checkCredentials(logindto.getEmail(),logindto.getPassword());
			String successMessage = "sucessfully login with"+logindto.getEmail();
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@PostMapping(value = "/sellers",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addSeller(@Valid @RequestBody SellerDTO sdto) throws Exception{
		try {
			
			String sd=SellerService.addseller(sdto);
			String successMessage = "seller details added successfully with " + sd ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	
	@DeleteMapping(value = "/seller/{sellerId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addSeller(@PathVariable String sellerId) throws Exception{
		try {
			
			SellerService.deleteSeller(sellerId);
			String successMessage = "seller details deleted successfully with " + sellerId;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	
	
	

	

}
