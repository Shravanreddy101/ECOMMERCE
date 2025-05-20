package com.codeWithProjects.ecom.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.OrderDTO;
import com.codeWithProjects.ecom.services.customer.cart.CartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TrackingController {

    @Autowired
    private CartService cartService;


    public TrackingController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("/order/{trackingId}")
    public ResponseEntity<OrderDTO> getOrderByTrackingId(@PathVariable UUID trackingId){
        OrderDTO orderDTO = cartService.searchOrderByTrackingId(trackingId);
        return ResponseEntity.ok().body(orderDTO);
    }
    
}
