package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.entity.Customer;
import com.jb.couponSystem.data.ex.CantBuyCouponException;
import com.jb.couponSystem.data.repository.CompanyRepository;
import com.jb.couponSystem.data.repository.CouponRepository;
import com.jb.couponSystem.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerServiceImpl extends ServiceAbs implements CustomerService {
    private CouponRepository couponRepository;
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public CustomerServiceImpl(CouponRepository couponRepository, CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Override
    public List<Coupon> customerCoupons() {
        List<Coupon> coupons = couponRepository.findByCustomerId(id);
        Map<Long, String> map = createMapCompaniesNamesById();
        for (Coupon coupon : coupons){
            if (coupon.getCompany() != null) {
                coupon.setCompanyName(map.get(coupon.getCompany().getId()));
            }
        }
        return coupons;
    }

    @Override
    public Coupon getCustomerCoupon(long couponId) {
        List<Coupon> customerCoupons = customerCoupons();
        for (Coupon coupon: customerCoupons) {
            if (coupon.getId() == couponId){
                coupon.setCompanyName(getCompanyNameById(coupon.getCompany().getId()));
                return coupon;

            }
        }

        return Coupon.empty();
    }

    @Override
    public Coupon getCustomerCouponForSale(long couponId) {
        List<Coupon> customerCouponsForSale = getCouponsForSale();
        for (Coupon c: customerCouponsForSale) {
            if (c.getId() == couponId){
                return c;
            }
        }
        return Coupon.empty();
    }

    @Transactional
    @Override
    public List<Coupon> getCouponsForSale() {
        List<Coupon> all = couponRepository.findAll();
        List<Coupon> couponsForSale= new ArrayList<>();
        for (Coupon c: all) {
            if (!userAlreadyHasCoupon(c) && c.getAmount() != 0){
                c.setCompanyName(getCompanyNameById(c.getCompany().getId()));
                couponsForSale.add(c);
            }
        }
        return couponsForSale;
    }

    @Transactional
    @Override
    public List<Coupon> customerCouponsByCategory(int category) {
        return couponRepository.findByCustomerIdAndCategory(id, category);
    }

    @Transactional
    @Override
    public List<Coupon> customerCouponsLessThan(double price) {
        return couponRepository.findByCustomerIdAndPriceLessThan(id, price);
    }

    @Transactional
    @Override
    public Coupon buyCoupon(long couponId) throws CantBuyCouponException {
        Coupon coupon = couponRepository.findById(couponId).orElse(Coupon.empty());
        if (coupon.getAmount()  <= 0 || userAlreadyHasCoupon(coupon)) {
            throw new CantBuyCouponException("Problem with buying the coupons");
        }
        Customer customer = customerRepository.findById(id).orElse(Customer.empty());
        if (customer.addCoupon(coupon)) {
            couponRepository.decreaseCouponAmount(couponId);
        }
        return coupon;
    }

    private boolean userAlreadyHasCoupon(Coupon coupon) {
        List<Coupon> customerCoupons = couponRepository.findByCustomerId(id);
        for (Coupon c : customerCoupons) {
            if (c.getId() == coupon.getId()) {
                return true;
            }
        }
        return false;
    }

    private Map<Long, String> createMapCompaniesNamesById(){
        Map<Long, String> companyNamesByIds  = new HashMap<Long, String>();
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies){
            companyNamesByIds.put(company.getId(), company.getName());
        }
        return companyNamesByIds;
    }

    private String getCompanyNameById(long id){
        return companyRepository.findById(id).get().getName();
    }
}
