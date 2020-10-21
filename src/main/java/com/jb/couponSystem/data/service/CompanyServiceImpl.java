package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.repository.CompanyRepository;
import com.jb.couponSystem.data.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyServiceImpl extends ServiceAbs implements CompanyService {
    private CouponRepository couponRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CouponRepository couponRepository, CompanyRepository companyRepository) {
        this.couponRepository = couponRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Override
    public List<Coupon> companyCoupons() {
        return couponRepository.findByCompanyId(id);
    }

    @Override
    public Coupon getCompanyCoupon(long couponId) {
        List<Coupon> companyCoupons = companyCoupons();
        for (Coupon c: companyCoupons) {
            if (c.getId() == couponId){
                return c;
            }
        }
        return Coupon.empty();
    }


    @Transactional
    @Override
    public List<Coupon> companyCouponsByCategory(int category) {
        return couponRepository.findByCompanyIdAndCategory(id, category);
    }

    @Transactional
    @Override
    public List<Coupon> companyCouponsLessThan(double price) {
        return couponRepository.findByCompanyIdAndPriceLessThan(id, price);
    }

    @Transactional
    @Override
    public List<Coupon> companyCouponsBeforeDate(Date endDate) {
        return couponRepository.findByCompanyIdAndEndDateBefore(id, endDate);
    }

    /**
     * adds coupon to the company. validation of endDate, startDate, amount and price will be in client side (UI).
     *
     * @param coupon to add
     * @return the coupon that added to the company
     */
    @Transactional
    @Override
    public Coupon addCompanyCoupon(Coupon coupon) {
        Optional<Company> optCompany = companyRepository.findById(id);
        if (coupon != null && optCompany.isPresent()) {
            coupon.setId(0);
            coupon.setCompany(optCompany.get());
            return couponRepository.save(coupon);
        }
        return Coupon.empty();
    }

    @Override
    public void deleteCoupon(long couponId) {
        List<Coupon> companyCoupons = couponRepository.findByCompanyId(id);
        for (Coupon c : companyCoupons) {
            if (c.getId() == couponId) {
                couponRepository.delete(c);
            }
        }
    }

    @Override
    public Company getCompany() {
        return companyRepository.findById(id).orElse(null);
    }
}
