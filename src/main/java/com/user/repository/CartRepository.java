package com.user.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.user.entity.Cart;
import com.user.entity.Wishlist;

import feign.Param;

public interface CartRepository extends CrudRepository<Cart, Integer> {
	
	List<Cart> findAllByBuyerId(String buyierid);
	
	void deleteAllByBuyerId(String buyerid);
	int deleteByBuyerIdAndProdId(String buyerid, String productid);
	Optional<Cart> findByBuyerIdAndProdId(String buyerId,String ProdId);
	//List<Buyer> findByCalledBy(long calledBy);
	@Modifying
	@Query("delete from Cart f where f.buyerId=:BuyerId and f.prodId=:prodId")
	 int deletecart(@Param("BuyerId") String BuyerId, @Param("prodId") String prodId);
	@Modifying
	@Query("delete from Cart f where f.buyerId=:BuyerId ")
	int deleteAllBuyerId(@Param("BuyerId") String BuyerId);
	
	
}

