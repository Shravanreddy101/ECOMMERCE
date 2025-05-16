package com.codeWithProjects.ecom.services.admin.coupon;

import java.util.List;

import com.codeWithProjects.ecom.dto.CouponDTO;
import com.codeWithProjects.ecom.entity.Coupon;

public interface AdminCouponService {


    public Coupon createCoupon(CouponDTO couponDTO);

    public List<Coupon> getAllCoupons();
}
