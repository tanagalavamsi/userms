package com.user.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.user.dto.WishlistDTO;
import com.user.entity.Buyer;
import com.user.entity.Cart;
import com.user.entity.Wishlist;
import com.user.feign.productMsfeign;
import com.user.repository.BuyerRepository;
import com.user.repository.CartRepository;
import com.user.repository.WishListRepository;

@Service
@Transactional
public class WishListService {
	@Autowired
	WishListRepository wishrepo;
	@Autowired
	BuyerRepository buyerRepository;
	@Autowired
	productMsfeign productMsfeign;
	@Autowired
	CartRepository cartRepo;
	public void checkProductExist(String buyerId,String ProdId) throws Exception
	{
		Optional<Wishlist> optional=wishrepo.findByBuyerIdAndProdId(buyerId,ProdId);
		if(optional.isPresent())
			throw new Exception("in the wishlist already exists with this productId:  "+ProdId);
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
			System.out.println(e);
			throw new Exception("In the app this product does not exist with the productId:    "+ProdId);
		}	
	}
	public void addtowishlist(String buyerid ,String Productid) throws Exception
	{		
		Wishlist wl=new Wishlist();
		wl.setBuyerId(buyerid);
		wl.setProdId(Productid);
		wishrepo.save(wl);
		}
	public static WishlistDTO Wishlist_entity_to_DTO(Wishlist wishlist)
	{
		WishlistDTO data=new  WishlistDTO();
		data.setBuyerId(wishlist.getBuyerId());
		data.setProdId(wishlist.getProdId());
		return data;	
	}
	public List<WishlistDTO> readWishList(String buyerid) throws Exception {	
        List<WishlistDTO> listDTO=new ArrayList<>();
        Optional<List<Wishlist>>optional =wishrepo.findAllByBuyerId(buyerid);
        List<Wishlist> list=optional.orElseThrow(() -> new Exception("no product is present in the wishlist of BuyerId:  "+buyerid));
        for(Wishlist wishlistdata:list)
        {	
        	WishlistDTO data=Wishlist_entity_to_DTO(wishlistdata);
        	listDTO.add(data);
        }
        if(listDTO.isEmpty())
        {
        	throw new Exception("no product is present in the wishlist of BuyerId:  "+buyerid);
        }
        return listDTO;
    }
	public void addtocart(WishlistDTO wishlistdto ) throws Exception 
	{
		int records = wishrepo.deleteFruits(wishlistdto.getBuyerId(),wishlistdto.getProdId());
		if(records>0)
		{
			Cart cart=new Cart();
			cart.setBuyerId(wishlistdto.getBuyerId());
			cart.setProdId(wishlistdto.getProdId());
			cart.setQuantity(1);
			cartRepo.save(cart);
		}
		else
		{
			throw new Exception("there is no item in wishlist with this id");
		}
	}
	}
