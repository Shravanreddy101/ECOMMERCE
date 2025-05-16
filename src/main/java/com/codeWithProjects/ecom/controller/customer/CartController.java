package com.codeWithProjects.ecom.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.AddProductInCartDTO;
import com.codeWithProjects.ecom.dto.CartItemsDTO;
import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.dto.PlaceOrderDTO;
import com.codeWithProjects.ecom.entity.CartItems;
import com.codeWithProjects.ecom.services.customer.cart.CartService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    
    @PostMapping("/cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDTO addProductInCartDTO) {
    try {
        CartItemsDTO cartItemDTO = cartService.addProductToCart(addProductInCartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTO);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
    }
}


    
    @GetMapping("/cart/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> getCartByUserId(@PathVariable Long userId){
        OrderDTO orderDTO = cartService.getCartByUserId(userId);
        return ResponseEntity.ok().body(orderDTO);
    }

    
    @GetMapping("/coupon/{userId}/{code}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> applyCoupon(@PathVariable Long userId, @PathVariable String code){
    OrderDTO orderDTO = cartService.applyCoupon(userId, code);
    return ResponseEntity.ok().body(orderDTO);
    } 


    @PostMapping("/addition")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> increaseProductQuantity(@RequestBody AddProductInCartDTO addProductInCartDTO){
        OrderDTO orderDTO = cartService.increaseProductQuantity(addProductInCartDTO);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/subtraction")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> decreaseProductQuantity(@RequestBody AddProductInCartDTO addProductInCartDTO){
        OrderDTO orderDTO = cartService.decreaseProductQuantity(addProductInCartDTO);
        return ResponseEntity.ok().body(orderDTO);
    }


    @PostMapping("/placeOrder")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        OrderDTO orderDTO = cartService.placeOrder(placeOrderDTO);
        return ResponseEntity.ok().body(orderDTO);
    }
        

}
