package com.codeWithProjects.ecom.dto;

import lombok.Data;

@Data
public class WishListDTO {

    private Long id;

    private Long productId;

    private Long userId;

    private String productName;

   private String productImgBase64;
    
    private Long price;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

   

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getProductImgBase64() {
        return productImgBase64;
    }

    public void setProductImgBase64(String productImgBase64) {
        this.productImgBase64 = productImgBase64;
    }
}
