package com.jb.couponSystem;

import com.jb.couponSystem.data.entity.Coupon;
import com.jb.couponSystem.data.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class CouponCleanerTask implements Runnable {
    private CouponRepository couponRepository;
    private boolean running;
    private static final int MILLIS_IN_DAY = 86400000;

    @Autowired
    public CouponCleanerTask(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            List<Coupon> expiredCoupons = couponRepository.findByEndDateBefore(new Date(System.currentTimeMillis()));
            for (Coupon c : expiredCoupons) {
                couponRepository.delete(c);
            }
            try {
                Thread.sleep(MILLIS_IN_DAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
