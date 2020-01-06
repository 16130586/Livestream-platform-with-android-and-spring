package com.t4.LiveServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class SubscriptionController {

    @GetMapping("/user/subscription/{userId}")
    public String subscriptionInfo(@PathVariable String userId) {
        return "subscription_info";
    }
}
