package com.user.controller;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.user.dto.BuyerDTO;
import com.user.dto.LoginDTO;
import com.user.service.UserService;
import com.user.service.WishListService;
@RestController
@RequestMapping(value="/api")
public class UserController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserService userService;
	@Autowired
	private Environment environment;
	@Autowired
	WishListService wishListService;
	@PostMapping(value = "/buyers/rewards/{buyerId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addrewards(@RequestBody Integer rewards,@PathVariable String buyerId) throws Exception{
		try {
			userService.addRewards(buyerId,rewards);
			String successMessage = "successfully added the rewards to the buyerId "+ buyerId ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}	
	}
	@PostMapping(value ="/buyer/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addBuyer( @Valid @RequestBody BuyerDTO sdto) throws Exception{
		try {
			String sd=userService.addbuyer(sdto);
			String successMessage = "You Have Successfully Registered With This"+ sd ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@GetMapping(value = "/buyer/{buyerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BuyerDTO> findBuyer(@PathVariable String buyerId) throws Exception{
		try {
			BuyerDTO buyerDTO= userService.getbuyerDetails(buyerId);
			return new ResponseEntity<>(buyerDTO, HttpStatus.OK);
		} 
		catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}
	@PostMapping(value = "/buyers/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loginbuyer(@RequestBody LoginDTO logindto) throws Exception{
		try {		
			userService.checkCredentials(logindto.getEmail(),logindto.getPassword());
			String successMessage = "sucessfully login with"+logindto.getEmail();
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@DeleteMapping(value = "/buyers/delete",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buyerdelete(@RequestBody String BuyerId) throws Exception{
		try {		
			userService.deleteAccount(BuyerId);
			String successMessage = "sucessfully deleted the account with Buyerid"+BuyerId;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@PostMapping(value = "/subscribe/buyers",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> subscribebuyer(@RequestBody subscribedproductDTO sdto) throws Exception{
		try {
			String successMessage=userService.subscribed(sdto) ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@GetMapping(value = "/subscribe/buyers/{BuyerId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<subscribedproductDTO>> subscribelist(@PathVariable String BuyerId) throws Exception{
		try {
			
			List<subscribedproductDTO> list=userService.listSubsribed(BuyerId);
			System.out.println(list);
			return new ResponseEntity<>(list, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	@PostMapping(value = "/update/address/{BuyerId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateAddress(@PathVariable String BuyerId,@RequestBody String Address) throws Exception{
		try {
			userService.updateAddress(BuyerId,Address);
			String successMessage = "successfully updates the address " ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}	
	}
	
}
