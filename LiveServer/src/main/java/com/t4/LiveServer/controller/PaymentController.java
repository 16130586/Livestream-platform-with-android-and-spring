package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    UserBusiness userBusiness;

    @PostMapping("/payment/ipn/subscription")
    @ResponseStatus(HttpStatus.OK)
    public void handleSubscriptionPaymentComplete(HttpServletRequest request) {
        Enumeration<String> param = request.getParameterNames();
        Map<String, String> value = new HashMap<>();
        while (param.hasMoreElements()) {
            String name = param.nextElement();
            value.put(name, request.getParameter(name));
        }
        String userId = request.getParameter("custom");
        String subscriptionId = request.getParameter("item_name");
        String numberOfMonth = request.getParameter("item_number");
        String amount = request.getParameter("payment_gross");
        userBusiness.upgradePremium(Integer.parseInt(userId), Integer.parseInt(subscriptionId), Integer.parseInt(numberOfMonth), Double.parseDouble(amount));

    }

    @PostMapping("/payment/ipn/ranking")
    @ResponseStatus(HttpStatus.OK)
    public void handleRankingPaymentComplete(HttpServletRequest request) {
        Enumeration<String> param = request.getParameterNames();
        Map<String, String> value = new HashMap<>();
        while (param.hasMoreElements()) {
            String name = param.nextElement();
            value.put(name, request.getParameter(name));
        }
        String userId = request.getParameter("custom");
        String point = request.getParameter("item_number");
        userBusiness.upRanking(Integer.parseInt(userId), Integer.parseInt(point));
    }

    @GetMapping("/payment/success")
    public String success() {
        return "payment_success";
    }
}
