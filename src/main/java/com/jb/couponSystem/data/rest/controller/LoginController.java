package com.jb.couponSystem.data.rest.controller;

import com.jb.couponSystem.data.entity.UserCredentials;
import com.jb.couponSystem.data.ex.InvalidLoginException;
import com.jb.couponSystem.data.rest.ClientSession;
import com.jb.couponSystem.data.rest.CouponSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController extends Controller {

    private static final int LENGTH_TOKEN = 10;
    private CouponSystem couponSystem;

    @Autowired
    public LoginController(@Qualifier("tokens") Map<String, ClientSession> tokensMap, CouponSystem couponSystem) {
        super(tokensMap);
        this.couponSystem = couponSystem;
    }

    @PostMapping("/login")
    public ResponseEntity<UserCredentials> login(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {
        ClientSession clientSession = couponSystem.login(email, password);
        String token = generateToken();
        tokensMap.put(token, clientSession);
        return ResponseEntity.ok(new UserCredentials(token, clientSession.getRole()));
    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<String> logout(@PathVariable String token) {
        ClientSession remove = tokensMap.remove(token);
        return ResponseEntity.ok("{\"status\":\"removed\"}");
    }

    private static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, LENGTH_TOKEN);
    }
}
