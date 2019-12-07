package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class NewPasswordController {

    @Autowired
    UserBusiness userBusiness;

    @GetMapping("/renewPassword/{userId}/{otp}")
    public String requestRenew(@PathVariable Integer userId, @PathVariable String otp, Model model) {
        User user = userBusiness.getUserById(userId);
        if (user.getForgotToken().equals(otp)) {
            model.addAttribute("isCorrectInfo",true);
        } else {
            model.addAttribute("isCorrectInfo",false);
        }
    return "forgot_form";
    }

    @PostMapping("/renewPassword/{userId}")
    public String reNewpassword(HttpServletRequest request, Model model, @PathVariable Integer userId) {
        User user = userBusiness.getUserById(userId);
        user.setPassword(new BCryptPasswordEncoder().encode(request.getParameter("newPassword")));
        user.setForgotToken("");
        userBusiness.saveUser(user);
        model.addAttribute("isSuccess", true);
        return "forgot_form";
    }
}
