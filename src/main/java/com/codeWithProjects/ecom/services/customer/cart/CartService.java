package com.codeWithProjects.ecom.services.customer.cart;

import org.springframework.http.ResponseEntity;

import com.codeWithProjects.ecom.dto.AddProductInCartDTO;
import com.codeWithProjects.ecom.dto.CartItemsDTO;
import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.dto.PlaceOrderDTO;


public interface CartService {

    public CartItemsDTO addProductToCart(AddProductInCartDTO addProductInCartDTO);
    
    public OrderDTO getCartByUserId(Long userId);

    public OrderDTO applyCoupon(Long userId, String code);

    public OrderDTO increaseProductQuantity(AddProductInCartDTO addProductInCartDTO);

    public OrderDTO decreaseProductQuantity(AddProductInCartDTO addProductInCartDTO);

    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO);
}

