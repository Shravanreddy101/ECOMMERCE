package com.codeWithProjects.ecom.services.customer.review;

import com.codeWithProjects.ecom.dto.OrderedProductsResponseDTO;

public interface ReviewService {

    public OrderedProductsResponseDTO getOrderedProductDetailsByOrderId(Long orderId);
}
