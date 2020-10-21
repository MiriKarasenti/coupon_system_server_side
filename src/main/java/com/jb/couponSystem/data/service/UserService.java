package com.jb.couponSystem.data.service;

import com.jb.couponSystem.data.entity.User;

public interface UserService {
    User getUserByEmailAndPassword(String email, String password);
}
