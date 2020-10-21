package com.jb.couponSystem.data.rest.controller;

import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.ex.CantBuyCouponException;
import com.jb.couponSystem.data.rest.ClientSession;
import com.jb.couponSystem.data.service.CustomerService;
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
public class CustomerController extends Controller {

    @Autowired
    public CustomerController(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        super(tokensMap);
    }

    @GetMapping("/customers/all/coupons/{token}")
    public ResponseEntity<List<Coupon>> getAllCoupons(@PathVariable String token) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        List<Coupon> coupons = customerService.getCouponsForSale();
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/customers/coupon/{token}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable String token, @RequestParam long couponId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        Coupon coupon = customerService.getCustomerCoupon(couponId);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/customers/coupon/sale/{token}")
    public ResponseEntity<Coupon> getCouponForSale(@PathVariable String token, @RequestParam long couponId) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        Coupon coupon = customerService.getCustomerCouponForSale(couponId);
        return ResponseEntity.ok(coupon);
    }

    @GetMapping("/customers/{token}")
    public ResponseEntity<List<Coupon>> customerCoupons(@PathVariable String token) {
        return customerCouponsByCategory(token, -1);
    }

    @GetMapping("/customers/coupons/category/{token}")
    public ResponseEntity<List<Coupon>> customerCouponsByCategory(@PathVariable String token, @RequestParam int category) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        List<Coupon> coupons;
        if (category == -1) {
            coupons = customerService.customerCoupons();
        } else {
            coupons = customerService.customerCouponsByCategory(category);
        }
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/customers/coupons/price/{token}")
    public ResponseEntity<List<Coupon>> customerCouponsLessThan(@PathVariable String token, @RequestParam double price) {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        List<Coupon> coupons = customerService.customerCouponsLessThan(price);
        if (coupons != null && coupons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("customers/buyCoupon/{token}")
    public ResponseEntity<Coupon> buyCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CantBuyCouponException {
        ClientSession clientSession = getSession(token);
        if (clientSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        clientSession.accessed();
        CustomerService customerService = (CustomerService) clientSession.getServiceAbs();
        Coupon buyCoupon = customerService.buyCoupon(coupon.getId());
        return ResponseEntity.ok(buyCoupon);
    }
}
