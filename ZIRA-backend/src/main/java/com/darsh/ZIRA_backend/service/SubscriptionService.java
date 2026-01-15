package com.darsh.ZIRA_backend.service;

import com.darsh.ZIRA_backend.modal.PlanType;
import com.darsh.ZIRA_backend.modal.Subscription;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.repository.SubscriptonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptonRepo subscriptonRepo;

    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptonRepo.save(subscription);
    }

    public Subscription getUserSubscription(Long userId) {
        Subscription subscription = subscriptonRepo.findByUserId(userId);

        if (!isValid(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionStartDate(LocalDate.now());
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }

        return subscriptonRepo.save(subscription);
    }

    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptonRepo.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if (planType.equals(PlanType.ANNUALLY)) {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        } else {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
        return subscriptonRepo.save(subscription);
    }

    public boolean isValid(Subscription subscription) {
        if (subscription.getPlanType().equals(PlanType.FREE)) return true;

        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currDate = LocalDate.now();

        return endDate.isAfter(currDate) || endDate.isEqual(currDate);
    }
}
