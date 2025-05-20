package com.codeWithProjects.ecom.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.FAQDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.dto.ReviewDTO;
import com.codeWithProjects.ecom.services.customer.CustomerProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.codeWithProjects.ecom.dto.WishListDTO;



@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerProductController {

    @Autowired
    private final CustomerProductService customerProductService;

    public CustomerProductController(CustomerProductService customerProductService){
        this.customerProductService = customerProductService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> productDTO = customerProductService.getAllProducts();
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDTO>> getAllProductByName(@PathVariable String name){
        List<ProductDTO> productDTO = customerProductService.searchProductByTitle(name);
        return ResponseEntity.ok().body(productDTO);
    }


    @GetMapping("/FAQ/{productId}")
    public ResponseEntity<List<FAQDTO>> getFAQ(@PathVariable Long productId){
        List<FAQDTO> faqDTO = customerProductService.getFAQ(productId);
        return ResponseEntity.ok().body(faqDTO);
    }


    @GetMapping("/reviews/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long productId){
        List<ReviewDTO> reviewDTOList = customerProductService.getReviews(productId);
        return ResponseEntity.ok().body(reviewDTOList);
    }



    @PostMapping("/addToWishList")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<WishListDTO> addProductsToWishList(@RequestBody WishListDTO wishListDTO){
        WishListDTO wishListDTO1 = customerProductService.addProductToWishList(wishListDTO);
        return ResponseEntity.ok().body(wishListDTO1);
    }

    @GetMapping("/wishlist/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<WishListDTO>> getWishlist(@PathVariable Long userId){
        List<WishListDTO> wishListDTOs = customerProductService.getWishList(userId);
        return ResponseEntity.ok().body(wishListDTOs);
    }

    @DeleteMapping("/removeFromWishlist/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeFromWishlist(@PathVariable Long id){
        customerProductService.removeItemFromWishList(id);
    }

    @DeleteMapping("/removeByProductId")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void removeByProductId(@RequestBody WishListDTO wishListDTO){
        customerProductService.removeByProductId(wishListDTO);
    }

}
