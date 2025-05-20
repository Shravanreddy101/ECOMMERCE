package com.codeWithProjects.ecom.entity;

import java.util.Base64;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.codeWithProjects.ecom.dto.WishListDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WishListDTO getDTO(){
        WishListDTO wishListDTO = new WishListDTO();
        wishListDTO.setId(id);
        wishListDTO.setProductId(product.getId());
        wishListDTO.setUserId(user.getId());

        if(product.getImg() != null){
        String base64Img = Base64.getEncoder().encodeToString(product.getImg());
        wishListDTO.setProductImgBase64("data:image/jpeg;base64," + base64Img);
    }
        wishListDTO.setProductName(product.getName());
        wishListDTO.setPrice(product.getPrice());
        return wishListDTO;
    }
}
