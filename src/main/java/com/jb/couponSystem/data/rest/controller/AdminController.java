package com.jb.couponSystem.data.rest.controller;

import com.jb.couponSystem.data.entity.Admin;
import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.entity.Customer;
import com.jb.couponSystem.data.ex.AlreadyExistException;
import com.jb.couponSystem.data.ex.CantDeleteException;
import com.jb.couponSystem.data.ex.UpdateFailedException;
import com.jb.couponSystem.data.rest.CategoriesUtil;
import com.jb.couponSystem.data.rest.CategoriesUtil.Category;
import com.jb.couponSystem.data.rest.ClientSession;
import com.jb.couponSystem.data.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController extends Controller {


    @Autowired
    public AdminController(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        super(tokensMap);
    }

    /*Companies methods*/
    @GetMapping("/admins/companies/getAll/{token}")
    public ResponseEntity<List<Company>> getAllCompanies(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.findAllCompanies());
    }

    @GetMapping("/admins/categories/getAll/{token}")
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        return ResponseEntity.ok(CategoriesUtil.getCategories());
    }

    @GetMapping("/admins/companies/get/{token}")
    public ResponseEntity<Company> getCompanyById(@PathVariable String token, @RequestParam long companyId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        Company company = adminService.findCompanyById(companyId);
        return ResponseEntity.ok(company);
    }


    @PostMapping("/admins/companies/add/{token}")
    public ResponseEntity<Company> createNewCompany(@PathVariable String token, @RequestBody Company company) throws AlreadyExistException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.createNewCompany(company));
    }

    @DeleteMapping("/admins/companies/delete/{token}")
    public ResponseEntity deleteCompanyById(@PathVariable String token, @RequestParam long companyId) throws CantDeleteException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        adminService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admins/companies/update/{token}")
    public ResponseEntity<Company> updateCompany(@PathVariable String token, @RequestBody Company company) throws UpdateFailedException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        Company updatedCompany = adminService.updateCompany(company);
        return ResponseEntity.ok(updatedCompany);
    }

    /*Coupons methods*/
    @GetMapping("/admins/coupons/{token}")
    public ResponseEntity<List<Coupon>> getAllCoupons(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.findAllCoupons());
    }

    @GetMapping("/admins/coupon/{token}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String token, @RequestParam long couponId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.getCoupon(couponId));
    }

    @GetMapping("/admins/coupons/byCompany/{token}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String token, @RequestParam long companyId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        List<Coupon> couponsByCompanyId = adminService.findCouponsByCompanyId(companyId);
        return ResponseEntity.ok(couponsByCompanyId);
    }

    @GetMapping("/admins/coupons/byCategory/{token}")
    public ResponseEntity<List<Coupon>> getCouponsByCategory(@PathVariable String token, @RequestParam int category) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        List<Coupon> coupons = adminService.findCouponByCategory(category);
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/admins/coupons/delete/{token}")
    public ResponseEntity deleteCoupon(@PathVariable String token, @RequestParam long couponId) throws CantDeleteException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        adminService.deleteCoupon(couponId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admins/coupons/add/{token}")
    public ResponseEntity<Coupon> createNewCoupon(@PathVariable String token, @RequestBody Coupon coupon, @RequestParam long companyId) throws AlreadyExistException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.createNewCoupon(coupon, companyId));
    }

    @GetMapping("/admins/coupons/byCustomer/{token}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable String token, @RequestParam long customerId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        List<Coupon> coupons = adminService.findAllCouponsByCustomerId(customerId);
        return ResponseEntity.ok(coupons);
    }

    /*Customers methods*/
    @GetMapping("/admins/customers/{token}")
    public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.findAllCustomers());
    }

    @GetMapping("/admins/customers/getCustomer/{token}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String token, @RequestParam long customerId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.findCustomerById(customerId));
    }

    @PostMapping("/admins/customers/add/{token}")
    public ResponseEntity<Customer> createNewCustomer(@PathVariable String token, @RequestBody Customer customer) throws AlreadyExistException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.createNewCustomer(customer));
    }

    @DeleteMapping("/admins/customers/delete/{token}")
    public ResponseEntity deleteCustomerById(@PathVariable String token, @RequestParam long customerId) throws CantDeleteException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        adminService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admins/customers/update/{token}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String token, @RequestBody Customer customer) throws UpdateFailedException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.updateCustomer(customer));
    }

    /*Admin methods*/
    @PostMapping("/admins/addAdmin/{token}")
    public ResponseEntity<Admin> createNewAdmin(@PathVariable String token, @RequestBody Admin admin) throws AlreadyExistException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.createNewAdmin(admin));
    }

    @DeleteMapping("/admins/deleteAdmin/{token}")
    public ResponseEntity deleteAdmin(@PathVariable String token, @RequestParam long adminId) throws CantDeleteException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/admins/updateAdmin/{token}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String token, @RequestBody Admin admin) throws UpdateFailedException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        return ResponseEntity.ok(adminService.updateAdmin(admin));
    }

    @GetMapping("/admins/getAdmin/{token}")
    public ResponseEntity<Admin> getAdminById(@PathVariable String token, @RequestParam long adminId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        AdminService adminService = (AdminService) clientSession.getServiceAbs();
        Admin admin = adminService.getAdmin(adminId);
        return ResponseEntity.ok(admin);
    }

}
