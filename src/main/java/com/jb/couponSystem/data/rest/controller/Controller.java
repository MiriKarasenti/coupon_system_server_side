package com.jb.couponSystem.data.rest.controller;

import com.jb.couponSystem.data.rest.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    protected Map<String, ClientSession> tokensMap;

    @Autowired
    public Controller(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        System.out.println("initial tokensMap: " + tokensMap);
        this.tokensMap = tokensMap;
    }

    protected ClientSession getSession(String token) {
        System.out.println("getting session for token: " + token);
        System.out.println("map is: " + tokensMap);
        return tokensMap.get(token);
    }
}
