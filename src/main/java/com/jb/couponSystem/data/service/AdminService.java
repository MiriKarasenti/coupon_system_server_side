package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.Admin;
import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.entity.Customer;
import com.jb.couponSystem.data.ex.AlreadyExistException;
import com.jb.couponSystem.data.ex.CantDeleteException;
import com.jb.couponSystem.data.ex.UpdateFailedException;

import java.util.List;

public interface AdminService {

    /*Company methods*/
    Company findCompanyById(long companyId);

    Company createNewCompany(Company company) throws AlreadyExistException;

    void deleteCompany(long companyId) throws CantDeleteException;

    Company updateCompany(Company company) throws UpdateFailedException;

    List<Company> findAllCompanies();

    /*Coupons methods*/
    List<Coupon> findCouponByCategory(int category);

    List<Coupon> findCouponsByCompanyId(long companyId);

    List<Coupon> findAllCoupons();

    Coupon getCoupon(long couponId);

    void deleteCoupon(long couponId) throws CantDeleteException;

    /*Customer methods*/
    Customer findCustomerById(long customerId);

    Customer createNewCustomer(Customer customer) throws AlreadyExistException;

    void deleteCustomer(long customerId) throws CantDeleteException;

    Customer updateCustomer(Customer customer) throws UpdateFailedException;

    List<Coupon> findAllCouponsByCustomerId(long customerId);

    List<Customer> findAllCustomers();

    /*Admin methods*/

    Admin createNewAdmin(Admin admin) throws AlreadyExistException;

    void deleteAdmin(long adminId) throws CantDeleteException;

    Admin updateAdmin(Admin admin) throws UpdateFailedException;

    Admin getAdmin(long adminId);

    Coupon createNewCoupon(Coupon coupon, long company_id);
}
