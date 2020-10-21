package com.jb.couponSystem.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "company")
public class Company extends Client{

    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Coupon> coupons;

    public Company() {
        coupons = new ArrayList<>();
    }

    public Company(long id){
        this();
        this.setId(id);
    }

    public static Company empty() {
        return new Company(ID_EMPTY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public void add(Coupon coupon) {
        coupon.setCompany(this);
        coupons.add(coupon);
    }
}
