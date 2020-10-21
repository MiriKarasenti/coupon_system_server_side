package com.jb.couponSystem.data.rest.controller;

import com.jb.couponSystem.data.entity.Company;
import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.rest.ClientSession;
import com.jb.couponSystem.data.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CompanyController extends Controller {

    @Autowired
    public CompanyController(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        super(tokensMap);
    }

    @GetMapping("/companies/coupons/{token}")
    public ResponseEntity<List<Coupon>> companyCoupons(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        List<Coupon> coupons = companyService.companyCoupons();
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/companies/coupon/{token}")
    public ResponseEntity<Coupon> companyCoupon(@PathVariable String token, @RequestParam long couponId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        Coupon coupon = companyService.getCompanyCoupon(couponId);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/companies/coupons/category/{token}")
    public ResponseEntity<List<Coupon>> companyCouponsByCategory(@PathVariable String token, @RequestParam int category) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        List<Coupon> coupons = companyService.companyCouponsByCategory(category);
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/companies/coupons/price/{token}")
    public ResponseEntity<List<Coupon>> companyCouponsLessThan(@PathVariable String token, @RequestParam double price) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        List<Coupon> coupons = companyService.companyCouponsLessThan(price);
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/companies/coupons/date/{token}")
    public ResponseEntity<List<Coupon>> companyCouponsBeforeDate(@PathVariable String token, @RequestParam Date date) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        List<Coupon> coupons = companyService.companyCouponsBeforeDate(date);
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/companies/addCoupon/{token}")
    public ResponseEntity<Coupon> addCompanyCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        Coupon companyCoupon = companyService.addCompanyCoupon(coupon);
        if (companyCoupon == null || companyCoupon.getId() == -1) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(companyCoupon);
    }

    @DeleteMapping("/companies/coupons/delete/{token}")
    public ResponseEntity<Company> deleteCoupon(@PathVariable String token, @RequestParam long couponId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CompanyService companyService = (CompanyService) clientSession.getServiceAbs();
        companyService.deleteCoupon(couponId);
        return ResponseEntity.ok(companyService.getCompany());
    }
}
