package com.jb.couponSystem.data.repository;

import com.jb.couponSystem.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
