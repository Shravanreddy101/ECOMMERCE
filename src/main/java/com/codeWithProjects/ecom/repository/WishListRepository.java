package com.codeWithProjects.ecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeWithProjects.ecom.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findAllByUserId(Long userId);

    WishList findByUserId(Long userId);

    void deleteByProductId(Long productId);

    Optional<WishList> findByProductId(Long productId);

    Optional<WishList> findByUserIdAndProductId(Long userId, Long productId);
}
