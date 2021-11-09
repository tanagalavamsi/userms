package com.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Buyer;
public interface BuyerRepository extends JpaRepository<Buyer, String> {
	int deleteByBuyerId(String BuyerId);
	Buyer findByEmailAndPassword(String email,String password);
	Optional<Buyer> findByEmailOrPhoneNo(String email,String phoenumber);
	Optional<Buyer> findByBuyerId(String buyerId);
	Buyer findTopByOrderByBuyerIdDesc();
}
