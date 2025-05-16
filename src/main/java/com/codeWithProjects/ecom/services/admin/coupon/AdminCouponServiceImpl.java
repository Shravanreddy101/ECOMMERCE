package com.codeWithProjects.ecom.services.admin.coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeWithProjects.ecom.dto.CouponDTO;
import com.codeWithProjects.ecom.entity.Coupon;
import com.codeWithProjects.ecom.repository.CouponRepository;

@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Autowired
    private final CouponRepository couponRepository;

    public AdminCouponServiceImpl(CouponRepository couponRepository){
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon createCoupon(CouponDTO couponDTO){
        boolean couponExists = couponRepository.existsByCode(couponDTO.getCode());
        if(couponExists){
            throw new IllegalArgumentException("Coupon already exists");
        }
        Coupon coupon = new Coupon();
        coupon.setCode(couponDTO.getCode());
        coupon.setDiscount(couponDTO.getDiscount());
        coupon.setName(couponDTO.getName());
        coupon.setExpirationDate(couponDTO.getExpirationDate());

        couponRepository.save(coupon);
        return coupon;
    }


    @Override
    public List<Coupon> getAllCoupons(){
        List<Coupon> coupon = couponRepository.findAll();
        return coupon;
    }

    
}
