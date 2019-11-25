package com.t4.LiveServer.controller;


import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.validation.form.RegistryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserBusiness userBusiness;
    @Autowired
    MailBusiness mailBusiness;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody Map<String, String> datas) {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="Login success!";
        response.data = userBusiness.login(datas.get("username"), datas.get("password"));
        return response;
    }

    @PostMapping("/registry")
    public ApiResponse registry(@Valid @RequestBody RegistryForm registryForm) {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message="Register success!";
        response.data = userBusiness.registry(registryForm);
        return response;
    }

    @GetMapping("/test")
    public String test() {
        return "success!";
    }

    @GetMapping("/not-role")
    public String t() {
        return "success!";
    }

    @GetMapping("/forgot/{email}")
    public ApiResponse forgotPassword(@PathVariable String email) {
        User user = userBusiness.getUserByGmail(email);
        ApiResponse response = new ApiResponse();
        if (user != null) {
            try {
                String forgotOtp = mailBusiness.sendMailForgot(email, "Forgot Password", user.getUserId());
                response.statusCode = 200;
                response.message = "Please check your mail!";
                user.setForgotToken(forgotOtp);
                userBusiness.saveUser(user);
            } catch (Exception e) {
                response.statusCode = 500;
                response.message = "something wrong! pls try later!";
                return response;
            }
        } else {
            response.statusCode = 400;
            response.message = "User not found! wrong mail";
        }
        return response;
    }
}
