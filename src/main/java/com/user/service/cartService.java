package com.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.user.dto.CartDTO;
import com.user.dto.productDTO;
import com.user.entity.Buyer;
import com.user.entity.Cart;
import com.user.feign.productMsfeign;
import com.user.repository.BuyerRepository;
import com.user.repository.CartRepository;


@Service
@Transactional
public class cartService {
	@Autowired
	CartRepository cartRepo;
	@Autowired
	BuyerRepository buyerRepository;
	@Autowired
	productMsfeign productMsfeign;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	private static final String TOPIC = "kafka-cartItemss";
	
	public  void checkcartexist(String buyerId,String ProdId) throws Exception
	{
		Optional<Cart> optional=cartRepo.findByBuyerIdAndProdId(buyerId,ProdId);
		if(optional.isPresent())
			throw new Exception("already exist with this productId:  "+ProdId+"in the cart");
	}
	public static CartDTO CartEntitytoDTO(Cart cart )
	{
		CartDTO cartdto=new CartDTO();
		cartdto.setQuantity(cart.getQuantity());
		cartdto.setBuyerId(cart.getBuyerId());
		cartdto.setProdId(cart.getProdId());
		return cartdto;
	}
	public static Cart CartDTOtoEntity(CartDTO cartDTO ) 
	{
		
		Cart cart1=new Cart();
		cart1.setQuantity(cartDTO.getQuantity());
		cart1.setBuyerId(cartDTO.getBuyerId());
		cart1.setProdId(cartDTO.getProdId());
		return cart1;
	}
	
	public void addToCartNormally(CartDTO cartDTO) throws Exception
	{	checkbuyerandproductExist(cartDTO.getBuyerId(),cartDTO.getProdId());
		checkcartexist(cartDTO.getBuyerId(),cartDTO.getProdId());
		Cart cart2=CartDTOtoEntity(cartDTO);
        cartRepo.save(cart2);
    }
	
	public void addToCart(CartDTO cartDTO) throws Exception
	{	checkbuyerandproductExist(cartDTO.getBuyerId(),cartDTO.getProdId());
		checkcartexist(cartDTO.getBuyerId(),cartDTO.getProdId());
		Cart cart2=CartDTOtoEntity(cartDTO);
        cartRepo.save(cart2);
        String message=cartDTO.getBuyerId()+" "+cartDTO.getProdId()+" "+cartDTO.getQuantity();
        kafkaTemplate.send(TOPIC,message);
        
		  System.out.println("sent cartItems" );
    }

	public List<CartDTO> listCartItems(String buyerid) throws Exception 
		{
			List<Cart> cartList = cartRepo.findAllByBuyerId(buyerid);
			List<CartDTO> cartItems = new ArrayList<>();		
			for (Cart Cart: cartList){
				CartDTO cartItemDto = CartEntitytoDTO(Cart);
				cartItems.add(cartItemDto);
			}
			if(cartItems.isEmpty())
			{
				throw new Exception("no product added in the cart with this buyerId"+buyerid);
			}
    	return cartItems;
		}

	public void updateCartItem(CartDTO cartDTO) throws Exception
	{
		Optional<Cart> optional = cartRepo.findByBuyerIdAndProdId(cartDTO.getBuyerId(),cartDTO.getProdId());
		Cart cart= optional.orElseThrow(() -> new Exception("data is not avaiaialbe with is buyerid and productid"));
		cart.setQuantity(cartDTO.getQuantity());
		
	}

	
	public void deleteAllbyuserid(String buyerid) throws Exception
	{
			
		int record=	cartRepo.deleteAllBuyerId(buyerid);;
		if(record<=0)
			{
					throw new Exception("no cart item present with this productid");
			}
			
	}

	public void deleteCartItem(String buyerId,String ProdId) throws Exception
	{	Optional<Cart> optional=cartRepo.findByBuyerIdAndProdId(buyerId,ProdId);
	
		if(optional.isPresent())
		{
		cartRepo.deletecart(buyerId,ProdId);	
		}
		else
		{
			throw new Exception("no cart item present with this buyerId"+buyerId);
		}
	}
	public  void checkbuyerandproductExist(String buyerId,String ProdId) throws Exception
	{
			
		Optional<Buyer> optional = buyerRepository.findByBuyerId(buyerId);
		if(optional.isEmpty())	
			throw new Exception("user does not exists with this buyerId");
		try
		{
		productMsfeign.getProduct(ProdId);
		}
		catch(Exception e)
		{
			throw new Exception("product does not exist with the productId:    "+ProdId);
		}
}
}




