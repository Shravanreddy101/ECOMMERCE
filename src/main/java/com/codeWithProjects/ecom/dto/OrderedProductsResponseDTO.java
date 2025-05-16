package com.codeWithProjects.ecom.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderedProductsResponseDTO {


    private List<ProductDTO> productDTOList;

    private Long orderAmount;
    
    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }
}
