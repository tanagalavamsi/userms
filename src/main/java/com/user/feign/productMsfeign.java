package com.user.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.user.controller.subscribedproductDTO;
import com.user.dto.productDTO;


@FeignClient(name="productms",url="http://localhost:8000")

public interface productMsfeign {
	@DeleteMapping(value = "/api/seller/{sellerId}")
	String deleteProductsbysellerId(@PathVariable String sellerId) throws Exception;
	
	@GetMapping(value="/api/{prodId}",   produces = MediaType.APPLICATION_JSON_VALUE)
	productDTO getProduct(@PathVariable("prodId") String prodId) throws Exception;
	@PostMapping(value = "product/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
	
	String addProducts(@RequestBody productDTO sdto) throws Exception;
	@DeleteMapping(value = "product/{prodId}/delete")
	String deleteProduct(@PathVariable String prodId) throws Exception ;
		
	@PutMapping(value = "/api/{prodId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	String updatestock(@PathVariable String prodId,@RequestBody Integer quantity ) throws Exception;
	
	@GetMapping(value = "/api/{category}/{subcategory}")
	List<productDTO> getProductbySubCategory(@PathVariable String category,@PathVariable String subcategory) throws Exception; 
	
	@GetMapping(value = "/api/name/{productName}",  produces = MediaType.APPLICATION_JSON_VALUE)
	List<productDTO> findProductByName(@PathVariable String productName) throws Exception;
	
	@GetMapping(value = "/api/{sellerId}/{prodId}/check")
	String verifyproductbysellerId(@PathVariable String sellerId,@PathVariable String prodId) throws Exception;

	@GetMapping(value = "/api/category/{category}",  produces = MediaType.APPLICATION_JSON_VALUE)
	List<productDTO> findproductbycategory(@PathVariable String category) throws Exception;
	
	@PostMapping(value = "/api/subscribe",  consumes = MediaType.APPLICATION_JSON_VALUE)
	String ProductSubsribeOrUpdate( @RequestBody subscribedproductDTO sdto) throws Exception;
	
	@GetMapping(value = "/api/subscribe/{buyerId}")
	List<subscribedproductDTO> listsubscribed(@PathVariable String buyerId) throws Exception ;
	
}
