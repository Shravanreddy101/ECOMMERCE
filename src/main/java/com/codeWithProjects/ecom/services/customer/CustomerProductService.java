package com.codeWithProjects.ecom.services.customer;

import java.util.List;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.dto.ReviewDTO;
import com.codeWithProjects.ecom.dto.WishListDTO;
import com.codeWithProjects.ecom.entity.WishList;

public interface CustomerProductService {

    public List<ProductDTO> searchProductByTitle(String title);

    public List<ProductDTO> getAllProducts();

    public List<FAQDTO> getFAQ(Long productId);

    public List<ReviewDTO> getReviews(Long productId);

    public WishListDTO addProductToWishList(WishListDTO wishListDTO);

    public List<WishListDTO> getWishList(Long userId);

    public void removeItemFromWishList(Long id);

    public void removeByProductId(WishListDTO wishListDTO);
}
