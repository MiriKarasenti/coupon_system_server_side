package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;

import java.sql.Date;
import java.util.List;

public interface CompanyService {
    List<Coupon> companyCoupons();

    Coupon getCompanyCoupon(long id);

    List<Coupon> companyCouponsByCategory(int category);

    List<Coupon> companyCouponsLessThan(double price);

    List<Coupon> companyCouponsBeforeDate(Date endDate);

    Coupon addCompanyCoupon(Coupon coupon);

    void deleteCoupon(long couponId);

    Company getCompany();
}
