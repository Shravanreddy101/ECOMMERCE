package com.codeWithProjects.ecom.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.OrderedProductsResponseDTO;
import com.codeWithProjects.ecom.services.customer.review.ReviewService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDTO> getOrderedProductDetailsByOrderId(@PathVariable Long orderId){
        OrderedProductsResponseDTO orderedProductsResponseDTO = reviewService.getOrderedProductDetailsByOrderId(orderId);
        return ResponseEntity.ok().body(orderedProductsResponseDTO);
    }
}
