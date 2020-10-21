package com.jb.couponSystem.data.entity;

import javax.persistence.Entity;

@Entity
public class Admin extends Client {

    private String email;
    private String password;

    public Admin() {
        /*empty*/
    }

    public Admin(long id) {
        setId(id);
    }

    public static Admin empty() {
        return new Admin(ID_EMPTY);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
