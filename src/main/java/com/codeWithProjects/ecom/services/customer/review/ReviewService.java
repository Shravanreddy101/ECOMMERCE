package com.codeWithProjects.ecom.services.customer.review;

import java.io.IOException;

import com.codeWithProjects.ecom.dto.OrderedProductsResponseDTO;
import com.codeWithProjects.ecom.dto.ReviewDTO;

public interface ReviewService {

    public OrderedProductsResponseDTO getOrderedProductDetailsByOrderId(Long orderId);

    public ReviewDTO giveReview(ReviewDTO reviewDTO) throws IOException;
}
