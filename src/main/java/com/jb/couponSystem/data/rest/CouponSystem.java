package com.jb.couponSystem.data.rest;

import com.jb.couponSystem.ClientSessionCleanerTask;
import com.jb.couponSystem.CouponCleanerTask;
import com.jb.couponSystem.data.entity.*;
import com.jb.couponSystem.data.ex.InvalidLoginException;
import com.jb.couponSystem.data.repository.UserRepository;
import com.jb.couponSystem.data.service.AdminServiceImpl;
import com.jb.couponSystem.data.service.CompanyServiceImpl;
import com.jb.couponSystem.data.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;

@Service
public class CouponSystem {
    private ApplicationContext context;
    private UserRepository userRepository;
    private ClientSessionCleanerTask clientSessionCleanerTask;
    private CouponCleanerTask couponCleanerTask;

    @Autowired
    public CouponSystem(ApplicationContext context, UserRepository userRepository) {
        this.context = context;
        this.userRepository = userRepository;
    }

    public ClientSession login(String email, String password) throws InvalidLoginException {
        Optional<User> optUser = userRepository.findByEmailAndPassword(email, password);
        ClientSession clientSession = context.getBean(ClientSession.class);
        if (!optUser.isPresent()) {
            throw new InvalidLoginException("no such user");
        }
        Client client = optUser.get().getClient();
        if (client instanceof Company) {
            clientSession.setServiceAbs(context.getBean(CompanyServiceImpl.class));
            clientSession.setRole("2");

        } else if (client instanceof Customer) {
            clientSession.setServiceAbs(context.getBean(CustomerServiceImpl.class));
            clientSession.setRole("1");

        } else if (client instanceof Admin) {
            clientSession.setServiceAbs(context.getBean(AdminServiceImpl.class));
            clientSession.setRole("3");

        } else {
            throw new InvalidLoginException("not exist!");
        }
        clientSession.accessed();
        clientSession.getServiceAbs().setId(client.getId());
        return clientSession;
    }

    @PostConstruct
    public void postConstruct() {
        clientSessionCleanerTask = context.getBean(ClientSessionCleanerTask.class);
        Thread thread1 = new Thread(clientSessionCleanerTask);
        thread1.start();

        couponCleanerTask = context.getBean(CouponCleanerTask.class);
        Thread thread2 = new Thread(couponCleanerTask);
        thread2.start();
    }

    @PreDestroy
    public void preDestroy() {
        clientSessionCleanerTask.setRunning(false);
        couponCleanerTask.setRunning(false);
    }
}
