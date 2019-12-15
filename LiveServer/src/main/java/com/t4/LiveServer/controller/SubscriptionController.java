package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class SubscriptionController {

    @Autowired
    UserBusiness userBusiness;

    @GetMapping("/user/subscription/{userId}")
    public String subscriptionInfo(@PathVariable String userId) {
        return "subscription_info";
    }


    @PostMapping("/payment/ipn")
    @ResponseStatus(HttpStatus.OK)
    public void handlePaymentComplete(HttpServletRequest request) {
        Enumeration<String> param = request.getParameterNames();
        Map<String, String> value = new HashMap<>();
        while (param.hasMoreElements()) {
            String name = param.nextElement();
            value.put(name, request.getParameter(name));
        }
        String userId = request.getParameter("custom");
        String subscriptionId = request.getParameter("item_name");
        String number = request.getParameter("item_number");
        String amount = request.getParameter("payment_gross");
        userBusiness.upgradePremium(Integer.parseInt(userId), Integer.parseInt(subscriptionId), Integer.parseInt(number), Double.parseDouble(amount));

    }

    @GetMapping("/payment/success")
    public String success() {
        return "payment_success";
    }
}
