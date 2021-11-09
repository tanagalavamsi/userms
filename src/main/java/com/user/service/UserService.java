package com.user.service;

import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.controller.subscribedproductDTO;
import com.user.dto.BuyerDTO;
import com.user.entity.Buyer;
import com.user.feign.productMsfeign;
import com.user.repository.BuyerRepository;
import com.user.repository.SellerRepository;
@Service
@Transactional
public class UserService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SellerRepository sellRepo;
	@Autowired
	BuyerRepository buyerReposiory;
	@Autowired
	productMsfeign ProductMsfeign;
	
	public void updateAddress(String BuyerId,String Address) throws Exception
	{
		Optional<Buyer> optional=buyerReposiory.findByBuyerId(BuyerId);
		Buyer buyer= optional.orElseThrow(() -> new Exception("buyer is not exist"));
		buyer.setAddress(Address);
	}
	public void addRewards(String buyierId,Integer rewards) throws Exception
	{
		Optional<Buyer> optional=buyerReposiory.findByBuyerId(buyierId);
		Buyer buyer= optional.orElseThrow(() -> new Exception("buyer is not exist"));
		int rewardss=buyer.getRewardPoints()+rewards;
		buyer.setRewardPoints(rewardss);
		if(rewardss>10000)
			buyer.setIsPriviliged("yes");
	}
	public static BuyerDTO Convert_Buyer_To_BuyerDTO(Buyer buyer)
	{
		BuyerDTO buyerDTO=new BuyerDTO();
		buyerDTO.setBuyerId(buyer.getBuyerId());
		buyerDTO.setEmail(buyer.getEmail());
		buyerDTO.setIsActive(buyer.getIsActive());
		buyerDTO.setIsPriviliged(buyer.getIsPriviliged());
		buyerDTO.setName(buyer.getName());
		buyerDTO.setPassword(buyer.getPassword());
		buyerDTO.setPhoneNo(buyer.getPhoneNo());
		buyerDTO.setRewardPoints(buyer.getRewardPoints());
		buyerDTO.setAddress(buyer.getAddress());
		return buyerDTO;
	}
	public static Buyer Convert_BuyerDTO_To_record(BuyerDTO buyerDTO)
	{
		Buyer buyer=new Buyer();
		buyer.setBuyerId(buyerDTO.getBuyerId());
		buyer.setEmail(buyerDTO.getEmail());
		buyer.setIsActive(buyerDTO.getIsActive());
		buyer.setName(buyerDTO.getName());
		buyer.setPassword(buyerDTO.getPassword());
		buyer.setPhoneNo(buyerDTO.getPhoneNo());
		return buyer;
	}
	public BuyerDTO getbuyerDetails(String buyerId) throws Exception
	{
		Optional<Buyer> optional= buyerReposiory.findByBuyerId(buyerId);
		Buyer buyer= optional.orElseThrow(() -> new Exception("buyer is not exist"));
		BuyerDTO buyerDTO=Convert_Buyer_To_BuyerDTO(buyer);
		return buyerDTO;
	}
	public String addbuyer(BuyerDTO buyerDTO) throws Exception{
	Optional<Buyer> optional= buyerReposiory.findByEmailOrPhoneNo(buyerDTO.getEmail(),buyerDTO.getPhoneNo());
		if(optional.isPresent())
		{
			throw new Exception("already emailId or phoneNumber Exist please try with new credentials");
		}
		Buyer buyer=Convert_BuyerDTO_To_record(buyerDTO);
		Buyer p=buyerReposiory.findTopByOrderByBuyerIdDesc();
		String buyerid=p.getBuyerId();
		String id =buyerid.substring(1);
        int num = Integer.parseInt(id);
        num=num+1;
        id="B"+num;
        buyer.setBuyerId(id);
        buyer.setRewardPoints(0);
        buyer.setIsPriviliged("no");
        buyer.setIsActive("yes");
		buyerReposiory.save(buyer);
		return buyer.getBuyerId();
		
	}
	public void deleteAccount(String BuyerId) throws Exception
	{
		int result= buyerReposiory.deleteByBuyerId(BuyerId);
		if(result==0)
		{
			throw new Exception("plz enter the valid BuyerId");
		}
	}
	
	public void checkCredentials(String email,String password) throws Exception
	{
		Buyer result= buyerReposiory.findByEmailAndPassword(email,password);
		if(result==null)
		{
			throw new Exception("Invalid credentials plz type correct email and password");
		}
	}
	public String  subscribed(subscribedproductDTO sdto) throws Exception{
		Optional<Buyer> optional=buyerReposiory.findByBuyerId(sdto.getBuyerId());
		Buyer buyer= optional.orElseThrow(() -> new Exception("buyer is not exist"));
		
		if(buyer.getIsPriviliged().toLowerCase().equals("no"))
		{
			throw new Exception("buyer does not have privilige permission");
		}
		
		String message=ProductMsfeign.ProductSubsribeOrUpdate(sdto);
		return message;
		
	}
	
	public List<subscribedproductDTO> listSubsribed(String buyerId) throws Exception
	{
		Optional<Buyer> optional=buyerReposiory.findByBuyerId(buyerId);
		Buyer buyer= optional.orElseThrow(() -> new Exception("buyer is not exist"));
		
		if(buyer.getIsPriviliged().toLowerCase().equals("no"))
		{
			throw new Exception("buyer does not have privilige permission");
		}
		return ProductMsfeign.listsubscribed(buyerId);
	}
	
	
	
}

