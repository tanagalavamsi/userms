package com.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.user.entity.Seller;

import feign.Param;

public interface SellerRepository extends JpaRepository<Seller, String> {

	Optional<Seller> findByEmailOrPhoneNo(String email,String string);
	
	@Query("select count(*) from Seller seller where seller.sellerId=:sellerId")
	int VerifySeller(@Param("sellerId") String sellerId);
	
	Seller findByEmailAndPassword(String email,String password);
	Optional<Seller> findBySellerId(String sellerId);
	Seller findTopByOrderBySellerIdDesc();
	int deleteBySellerId(String sellerId);
}

