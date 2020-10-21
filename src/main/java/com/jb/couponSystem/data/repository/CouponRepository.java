package com.jb.couponSystem.data.repository;

import com.jb.couponSystem.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /*Company Related methods.*/

    List<Coupon> findByCompanyId(long companyId);

    List<Coupon> findByCompanyIdAndCategory(long companyId, int category);

    List<Coupon> findByCompanyIdAndPriceLessThan(long companyId, double price);

    List<Coupon> findByCompanyIdAndEndDateBefore(long companyId, Date date);

    /*Customer Related methods.*/
    @Query("select c from Customer as cust join cust.coupons as c where cust.id=:customerId")
    List<Coupon> findByCustomerId(long customerId);

    @Query("select c from Customer as cust join cust.coupons as c where cust.id=:customerId and c.category=:category")
    List<Coupon> findByCustomerIdAndCategory(long customerId, int category);

    @Query("select c from Customer as cust join cust.coupons as c where cust.id=:customerId and c.price<:price")
    List<Coupon> findByCustomerIdAndPriceLessThan(long customerId, double price);

    /*General methods.*/
    List<Coupon> findByEndDateBefore(Date date);

    List<Coupon> findByCategory(int category);

    @Transactional
    @Modifying
    @Query("update Coupon set amount = amount-1 where id=:couponId")
    void decreaseCouponAmount(long couponId);
}
