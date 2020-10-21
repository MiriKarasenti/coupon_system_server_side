package com.jb.couponSystem.data.rest;

import com.jb.couponSystem.data.service.ServiceAbs;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClientSession {
    private ServiceAbs serviceAbs;
    private long lastAccessedMillis;
    private String role;

    public ClientSession(){

    }
    public void accessed(){
        this.lastAccessedMillis = System.currentTimeMillis();
    }

    public ServiceAbs getServiceAbs() {
        return serviceAbs;
    }

    public void setServiceAbs(ServiceAbs serviceAbs) {
        this.serviceAbs = serviceAbs;
    }

    public long getLastAccessedMillis() {
        return lastAccessedMillis;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
