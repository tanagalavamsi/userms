package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.user.entity.Wishlist;

import feign.Param;

public interface WishListRepository extends CrudRepository<Wishlist,String>{
	Optional<List<Wishlist>> findAllByBuyerId(String buyierid);
	//public Optional<Wishlist> deleteByBuyerIdAndProdId(String buyerid,String productid);
	//public List<Wishlist> findAllByBuyerIdAndProdId(String buyierid);
	
	Optional<Wishlist> findByBuyerIdAndProdId(String buyerId,String ProdId);
	@Modifying
	@Query("delete from Wishlist f where f.buyerId=:BuyerId and f.prodId=:prodId")
	 int deleteFruits(@Param("BuyerId") String BuyerId, @Param("prodId") String prodId);

}
