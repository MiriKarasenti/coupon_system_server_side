package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.ex.CantBuyCouponException;

import java.util.List;

public interface CustomerService {

    List<Coupon> customerCoupons();

    Coupon getCustomerCoupon(long couponId);

    List<Coupon> customerCouponsByCategory(int category);

    List<Coupon> customerCouponsLessThan(double price);

    Coupon buyCoupon(long couponId) throws CantBuyCouponException;

    List<Coupon> getCouponsForSale();

    Coupon getCustomerCouponForSale(long couponId);
}
