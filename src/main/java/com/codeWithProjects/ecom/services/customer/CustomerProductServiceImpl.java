package com.codeWithProjects.ecom.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.codeWithProjects.ecom.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.dto.ReviewDTO;
import com.codeWithProjects.ecom.dto.WishListDTO;
import com.codeWithProjects.ecom.entity.FAQ;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.entity.Review;
import com.codeWithProjects.ecom.entity.User;
import com.codeWithProjects.ecom.entity.WishList;
import com.codeWithProjects.ecom.repository.FAQRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;
import com.codeWithProjects.ecom.repository.ReviewRepository;
import com.codeWithProjects.ecom.repository.UserRepository;

@Service
public class CustomerProductServiceImpl implements CustomerProductService{

   

    @Autowired
    private final ProductRepository productRepository;
    private final FAQRepository faqRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;

    public CustomerProductServiceImpl(ProductRepository productRepository, FAQRepository faqRepository,ReviewRepository reviewRepository, UserRepository userRepository, WishListRepository wishListRepository){
        this.productRepository = productRepository;
        this.faqRepository = faqRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
    }



    @Override
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> searchProductByTitle(String name){
        List<Product> products = productRepository.getAllByNameContaining(name);
        return products.stream().map(Product::getDTO).collect(Collectors.toList());
    }

    @Override
    public List<FAQDTO> getFAQ(Long productId){
        List<FAQ> faq = faqRepository.findAllByProductId(productId);
        return faq.stream().map(FAQ::getDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviews(Long productId){
        List<Review> reviewList = reviewRepository.findAllByProductId(productId);
        return reviewList.stream().map(Review::getDTO).collect(Collectors.toList());
    }


    @Override
    public WishListDTO addProductToWishList(WishListDTO wishListDTO){
        Optional<Product> optionalProduct = productRepository.findById(wishListDTO.getProductId());
        Optional<User> optionalUser = userRepository.findById(wishListDTO.getUserId());

        Optional<WishList> optionalWishList = wishListRepository.findByUserIdAndProductId(optionalUser.get().getId(), optionalProduct.get().getId());
        if(optionalWishList.isPresent()){
            throw new IllegalArgumentException("Product already in wishlist");
        }
        
        WishList wishList = new WishList();
        wishList.setProduct(optionalProduct.get());
        wishList.setUser(optionalUser.get());
        wishListRepository.save(wishList);
        
        return wishList.getDTO();
    }


    @Override
    public List<WishListDTO> getWishList(Long userId){
        List<WishList> wishList = wishListRepository.findAllByUserId(userId);
        return wishList.stream().map(WishList::getDTO).collect(Collectors.toList());
    }


    @Override
    public void removeItemFromWishList(Long id){
        Optional<WishList> wishlist = wishListRepository.findById(id);
        wishListRepository.deleteById(wishlist.get().getId());

    }

    @Override
    public void removeByProductId(WishListDTO wishListDTO){
        Optional<WishList> wishlist = wishListRepository.findByUserIdAndProductId(wishListDTO.getUserId(), wishListDTO.getProductId());
        wishListRepository.deleteById(wishlist.get().getId());
    }

    
}
