package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.*;
import com.jb.couponSystem.data.ex.AlreadyExistException;
import com.jb.couponSystem.data.ex.CantDeleteException;
import com.jb.couponSystem.data.ex.UpdateFailedException;
import com.jb.couponSystem.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdminServiceImpl extends ServiceAbs implements AdminService {

    private AdminRepository adminRepository;
    private CustomerRepository customerRepository;
    private CompanyRepository companyRepository;
    private CouponRepository couponRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,
                            CustomerRepository customerRepository,
                            CompanyRepository companyRepository,
                            CouponRepository couponRepository,
                            UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;

    }

    @Override
    public Company findCompanyById(long companyId) {
        return companyRepository.findById(companyId).orElse(Company.empty());
    }

    @Override
    public Company createNewCompany(Company company) throws AlreadyExistException {
        if (emailAlreadyExist(company.getEmail())) {
            throw new AlreadyExistException(company.getEmail() + " is already exist in the system");
        }
        company.setId(0);
        List<Coupon> coupons = company.getCoupons();
        company.setCoupons(new ArrayList<>());
        Company savedCompany = companyRepository.save(company);
        userRepository.save(new User(savedCompany.getEmail(), savedCompany.getPassword(), savedCompany));
        if (null != coupons) {
            for (Coupon c : coupons) {
                c.setId(0);
                c.setCompany(savedCompany);
                couponRepository.save(c);
            }
        }
        savedCompany.setCoupons(coupons);
        return savedCompany;
    }

    @Override
    public void deleteCompany(long companyId) throws CantDeleteException {
        Optional<Company> optCompany = companyRepository.findById(companyId);
        if (optCompany.isPresent()) {
            userRepository.delete(userRepository.findByEmail(optCompany.get().getEmail()));
            companyRepository.delete(optCompany.get());
        } else {
            throw new CantDeleteException("company doesn't exist");
        }
    }

    /**
     * update company details
     *
     * @param company to update
     * @return updated company
     */
    @Override
    public Company updateCompany(Company company) throws UpdateFailedException {
        Optional<Company> optCompany = companyRepository.findById(company.getId());
        if (optCompany.isPresent()) {
            if (!optCompany.get().getEmail().equals(company.getEmail())) {
                throw new UpdateFailedException("not allowed to update email");
            }
            company.setCoupons(optCompany.get().getCoupons());
            Company updatedCompany = companyRepository.save(company);
            User user = userRepository.findByEmail(updatedCompany.getEmail());
            user.setPassword(updatedCompany.getPassword());
            userRepository.save(user);
            return updatedCompany;
        }
        throw new UpdateFailedException("no such company");
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Coupon> findCouponByCategory(int category) {
        return couponRepository.findByCategory(category);
    }

    @Override
    public List<Coupon> findCouponsByCompanyId(long companyId) {
        return couponRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        Map<Long, String> map = createMapCompaniesNamesById();
        for (Coupon coupon : coupons){
            if (coupon.getCompany() != null) {
                coupon.setCompanyName(map.get(coupon.getCompany().getId()));
            }
        }
        return coupons;
    }

    @Override
    public Coupon getCoupon(long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElse(Coupon.empty());
        if (coupon.getCompany() != null){
            coupon.setCompanyName(getCompanyNameById(coupon.getCompany().getId()));
        }
        return coupon;
    }

    @Override
    public void deleteCoupon(long couponId) throws CantDeleteException {
        Optional<Coupon> optCoupon = couponRepository.findById(couponId);
        if (!optCoupon.isPresent()) {
            throw new CantDeleteException("coupon doesn't exist");
        }
        couponRepository.deleteById(couponId);
    }

    @Override
    public Customer findCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElse(Customer.empty());
    }

    @Override
    public Customer createNewCustomer(Customer customer) throws AlreadyExistException {
        if (emailAlreadyExist(customer.getEmail())) {
            throw new AlreadyExistException(customer.getEmail() + " already exist in the system");
        }
        customer.setId(0);
        Customer savedCustomer = customerRepository.save(customer);
        userRepository.save(new User(savedCustomer.getEmail(), savedCustomer.getPassword(), savedCustomer));
        return savedCustomer;
    }

    @Override
    public void deleteCustomer(long customerId) throws CantDeleteException {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);
        if (optCustomer.isPresent()) {
            userRepository.delete(userRepository.findByEmail(optCustomer.get().getEmail()));
            customerRepository.delete(optCustomer.get());
        } else {
            throw new CantDeleteException("customer doesn't exist");
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) throws UpdateFailedException {
        Optional<Customer> optCustomer = customerRepository.findById(customer.getId());
        if (optCustomer.isPresent()) {
            if (!optCustomer.get().getEmail().equals(customer.getEmail())) {
                throw new UpdateFailedException("not allowed to update email");
            }
            customer.setCoupons(optCustomer.get().getCoupons());
            Customer updatedCustomer = customerRepository.save(customer);
            User user = userRepository.findByEmail(updatedCustomer.getEmail());
            user.setPassword(updatedCustomer.getPassword());
            userRepository.save(user);
            return updatedCustomer;
        }
        throw new UpdateFailedException("no such customer");
    }

    @Override
    public List<Coupon> findAllCouponsByCustomerId(long customerId) {
        return couponRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Admin createNewAdmin(Admin admin) throws AlreadyExistException {
        if (emailAlreadyExist(admin.getEmail())) {
            throw new AlreadyExistException(admin.getEmail() + " already exist in the system");
        }
        admin.setId(0);
        Admin savedAdmin = adminRepository.save(admin);
        userRepository.save(new User(savedAdmin.getEmail(), savedAdmin.getPassword(), savedAdmin));
        return savedAdmin;
    }

    @Override
    public void deleteAdmin(long adminId) throws CantDeleteException {
        Optional<Admin> optAdmin = adminRepository.findById(adminId);
        if (optAdmin.isPresent() && id != optAdmin.get().getId()) {
            userRepository.delete(userRepository.findByEmail(optAdmin.get().getEmail()));
            adminRepository.delete(optAdmin.get());
        } else if (optAdmin.isPresent() && id == optAdmin.get().getId()) {
            throw new CantDeleteException("not possible to delete yourself :)");
        } else {
            throw new CantDeleteException("admin doesn't exist");
        }
    }

    @Override
    public Admin updateAdmin(Admin admin) throws UpdateFailedException {
        Optional<Admin> optAdmin = adminRepository.findById(admin.getId());
        if (optAdmin.isPresent()) {
            if (!optAdmin.get().getEmail().equals(admin.getEmail())) {
                throw new UpdateFailedException("not allowed to update email");
            }
            Admin updatedAdmin = adminRepository.save(admin);
            User user = userRepository.findByEmail(updatedAdmin.getEmail());
            user.setPassword(updatedAdmin.getPassword());
            userRepository.save(user);
            return updatedAdmin;
        }
        throw new UpdateFailedException("no such admin");
    }

    @Override
    public Admin getAdmin(long adminId) {
        return adminRepository.findById(adminId).orElse(Admin.empty());
    }

    @Override
    public Coupon createNewCoupon(Coupon coupon, long company_id) {
        Optional<Company> optCompany = companyRepository.findById(company_id);
        if (coupon != null && optCompany.isPresent()) {
            coupon.setId(0);
            coupon.setCompany(optCompany.get());
            return couponRepository.save(coupon);
        }
        return Coupon.empty();
    }

    private boolean emailAlreadyExist(String email) {
        User user = userRepository.findByEmail(email);
        return null != user;
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
