package com.jb.couponSystem.data.entity;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;

    @Any(metaColumn = @Column(name = "role"))
    @AnyMetaDef(idType = "long", metaType = "int", metaValues = {
            @MetaValue(value = "1", targetEntity = Customer.class),
            @MetaValue(value = "2", targetEntity = Company.class),
            @MetaValue(value = "3", targetEntity = Admin.class)})
    @JoinColumn(name = "client_id")
    private Client client;

    public User() {
        /*Empty*/
    }

    public User(String email, String password, Client client) {
        this.email = email;
        this.password = password;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
