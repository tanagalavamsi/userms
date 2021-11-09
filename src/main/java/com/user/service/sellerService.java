package com.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dto.SellerDTO;
import com.user.dto.productDTO;
import com.user.dto.quantityDTO;
import com.user.entity.Seller;
import com.user.feign.productMsfeign;
import com.user.repository.SellerRepository;

@Service
@Transactional
public class sellerService {
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	productMsfeign productMsfeign;
	public static Seller Convert_SellerDTO_To_record(SellerDTO sellerDTO)
	{
		Seller seller=new Seller();
		seller.setsellerId(sellerDTO.getsellerId());
		seller.setActive("YES");
		seller.setEmail(sellerDTO.getEmail());
		seller.setName(sellerDTO.getName());
		seller.setPassword(sellerDTO.getPassword());
		seller.setPhoneNo(sellerDTO.getPhoneNo());
		return seller;
	}
	public String addseller(SellerDTO sellerDTO) throws Exception{
		Optional<Seller> optional=sellerRepository.findByEmailOrPhoneNo(sellerDTO.getEmail(), sellerDTO.getPhoneNo());
		if(optional.isPresent())
		{
			throw new Exception("already emailId or phoneNumber Exist please try with new credentials");
		}
		Seller p= sellerRepository.findTopByOrderBySellerIdDesc();
		String sellerid=p.getsellerId();
		String id =sellerid.substring(1);
        int num = Integer.parseInt(id);
        num=num+1;
        id="S"+num;
		Seller seller=Convert_SellerDTO_To_record(sellerDTO);
		seller.setsellerId(id);
		sellerRepository.save(seller);
		return seller.getsellerId();
		
	}
	public void verifyseller(String sellerId) throws Exception
	{
		Optional<Seller> optional = sellerRepository.findBySellerId(sellerId);
	
			if(optional.isEmpty())
			{
				throw new Exception("seller does not exists");
			}
		
	}
	public void checkCredentials(String email,String password) throws Exception
	{
		Seller result= sellerRepository.findByEmailAndPassword(email,password);
		if(result==null)
		{
			throw new Exception("Invalid credentials plz type correct email and password");
		}
	}
	public static SellerDTO seller_to_DTO(Seller seller)
	{
		SellerDTO s =new SellerDTO();
		s.setActive(seller.getIsActive());
		s.setEmail(seller.getEmail());
		s.setName(seller.getName());
		s.setPhoneNo(seller.getPhoneNo());
		s.setPassword(seller.getPassword());
		s.setsellerId(s.getsellerId());
		s.setsellerId(seller.getsellerId());
		return s;
	}
	public void AddProudct(productDTO ProductDTO,String sellerId ) throws Exception
	{
		try
		{	
			verifyseller(sellerId);
			
			ProductDTO.setSellerId(sellerId);
			productMsfeign.addProducts(ProductDTO);
		}
		catch(Exception exception)
		{
			throw exception;
		}
	}
	public SellerDTO getsellerDetails(String sellerId) throws Exception
	{
		Optional<Seller> optional= sellerRepository.findBySellerId(sellerId);
		Seller seller= optional.orElseThrow(() -> new Exception("seller is not exist"));
		SellerDTO s=seller_to_DTO(seller);
		return s;
	}
	public void DeleteProduct(String sellerId,String prodId) throws Exception
	{
		try
		{
		verifyseller(sellerId);
	
		productMsfeign.verifyproductbysellerId(sellerId,prodId);
	
		productMsfeign.deleteProduct(prodId);
	
		}
		catch(Exception exception)
		{
			throw new Exception("sellerId does not belong to the product or seller Does not exist");
		}
	}
	public void updateProductbyid(String sellerId,quantityDTO QuantityDTO) throws Exception
	{	
		try
		{
			verifyseller(sellerId);
			productMsfeign.verifyproductbysellerId(sellerId,QuantityDTO.getProdId());
			productMsfeign.updatestock(QuantityDTO.getProdId(),QuantityDTO.getQuantity());
		}
		catch(Exception exception) {
			throw new Exception("sellerId does not belong to the product or seller Does not exist");
		}
	}
	public productDTO getproduct(String sellerId,String prodId) throws Exception
	{
		try
		{
			verifyseller(sellerId);
			productMsfeign.verifyproductbysellerId(sellerId,prodId);
			productDTO p=productMsfeign.getProduct(prodId);
			return p;
		}
		catch(Exception exception)
		{
			throw new Exception("sellerId does not belong to the product or seller Does not exist");
		}
	}
	public void deleteSeller(String sellerId) throws Exception
	{
		Optional<Seller> optional=sellerRepository.findBySellerId(sellerId);
		if(optional.isEmpty())
			throw new Exception("no buyer is present with this data");
		sellerRepository.deleteBySellerId(sellerId);
		productMsfeign.deleteProductsbysellerId(sellerId);	
	}
}
