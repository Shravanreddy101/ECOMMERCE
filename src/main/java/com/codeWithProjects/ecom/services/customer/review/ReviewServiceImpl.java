package com.codeWithProjects.ecom.services.customer.review;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.OrderedProductsResponseDTO;
import com.codeWithProjects.ecom.dto.ProductDTO;
import com.codeWithProjects.ecom.entity.CartItems;
import com.codeWithProjects.ecom.entity.Order;
import com.codeWithProjects.ecom.repository.OrderRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final OrderRepository orderRepository;

    public ReviewServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
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
}
