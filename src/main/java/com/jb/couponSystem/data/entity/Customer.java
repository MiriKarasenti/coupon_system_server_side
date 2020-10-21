package com.jb.couponSystem.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends Client {


    private String firstName;
    private String lastName;
    @JsonProperty(value = "coupon")
    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "customer_coupon", joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;

    public Customer() {
        coupons = new ArrayList<>();
    }

    public Customer(long id) {
        this();
        this.setId(id);
    }

    public static Customer empty() {
        return new Customer(ID_EMPTY);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean addCoupon(Coupon coupon) {
        return coupons.add(coupon);
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
