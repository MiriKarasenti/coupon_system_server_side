package com.jb.couponSystem.data.repository;

import com.jb.couponSystem.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<com.jb.couponSystem.data.entity.User> findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
