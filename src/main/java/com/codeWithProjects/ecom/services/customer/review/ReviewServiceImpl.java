package com.codeWithProjects.ecom.services.customer.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.OrderedProductsResponseDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.dto.ReviewDTO;
import com.codeWithProjects.ecom.entity.CartItems;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.entity.Product;
import com.codeWithProjects.ecom.entity.Review;
import com.codeWithProjects.ecom.entity.User;
import com.codeWithProjects.ecom.repository.OrderRepository;
import com.codeWithProjects.ecom.repository.ProductRepository;
import com.codeWithProjects.ecom.repository.ReviewRepository;
import com.codeWithProjects.ecom.repository.UserRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public OrderedProductsResponseDTO getOrderedProductDetailsByOrderId(Long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        OrderedProductsResponseDTO orderedProductsResponseDTO = new OrderedProductsResponseDTO();
        if(optionalOrder.isPresent()){
            orderedProductsResponseDTO.setOrderAmount(optionalOrder.get().getAmount());
            
            List<ProductDTO> productDTOList = new ArrayList<>();

            for(CartItems cartItems: optionalOrder.get().getCartItems()){
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(cartItems.getProduct().getId());
                productDTO.setName(cartItems.getProduct().getName());
                productDTO.setPrice(cartItems.getProduct().getPrice());
                productDTO.setQuantity(cartItems.getQuantity());
                productDTO.setByteImg(cartItems.getProduct().getImg());

                productDTOList.add(productDTO);
            }
            orderedProductsResponseDTO.setProductDTOList(productDTOList);
        }
        return orderedProductsResponseDTO;
    }


    @Override
    public ReviewDTO giveReview(ReviewDTO reviewDTO) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(reviewDTO.getProductId());
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());

        if(optionalProduct.isPresent() && optionalUser.isPresent()) {
            Review review = new Review();
            review.setRating(reviewDTO.getRating());
            review.setDescription(reviewDTO.getDescription());
            review.setImg(reviewDTO.getImg().getBytes());
            review.setProduct(optionalProduct.get());
            review.setUser(optionalUser.get());
            
            return reviewRepository.save(review).getDTO();
        } 

        return reviewDTO;
    }
}
