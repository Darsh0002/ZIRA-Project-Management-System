package com.darsh.ZIRA_backend.controller;

import com.darsh.ZIRA_backend.modal.PlanType;
import com.darsh.ZIRA_backend.modal.Subscription;
import com.darsh.ZIRA_backend.modal.User;
import com.darsh.ZIRA_backend.service.SubscriptionService;
import com.darsh.ZIRA_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return ResponseEntity.ok(subscription);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeUserSubscription(
            @RequestParam PlanType planType,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);

        return ResponseEntity.ok(subscription);
    }
}
