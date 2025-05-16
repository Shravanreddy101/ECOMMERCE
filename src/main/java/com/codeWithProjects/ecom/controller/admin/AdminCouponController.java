package com.codeWithProjects.ecom.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import com.codeWithProjects.ecom.dto.CouponDTO;
import com.codeWithProjects.ecom.entity.Coupon;
import com.codeWithProjects.ecom.services.admin.coupon.AdminCouponService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/admin/coupons")
public class AdminCouponController {

    @Autowired
    private final AdminCouponService adminCouponService;


    public AdminCouponController(AdminCouponService adminCouponService){
        this.adminCouponService = adminCouponService;
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponDTO couponDTO){
        try{
            Coupon coupon = adminCouponService.createCoupon(couponDTO);
            return ResponseEntity.ok(coupon);
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }
}
