package com.t4.LiveServer.controller;


import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.validation.form.RegistryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserBusiness userBusiness;
    @Autowired
    MailBusiness mailBusiness;


    @PostMapping("/login")
    public ApiResponse login(@RequestBody Map<String, String> datas) { 
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "Login success!";
        response.data = userBusiness.login(datas.get("username"), datas.get("password"));
        return response;
    }

    @PostMapping("/registry")
    public ApiResponse registry(@Valid @RequestBody RegistryForm registryForm) {
        ApiResponse response = new ApiResponse();
        response.statusCode = 200;
        response.message = "Register success!";
        response.data = userBusiness.registry(registryForm);
        return response;
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


    @GetMapping("/auth/info")
    public ApiResponse getInfo(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get user info!";
        apiResponse.data = userBusiness.getUserById(user.getUserId());
        return apiResponse;
    }
	
    //Tuan update ham getInfoID cho moi channel đứng mẹ r :V vl, chịu
	@GetMapping("/info/{id}")
	public ApiResponse getInfoID(@PathVariable(name = "id") int id) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.statusCode = 200;
		apiResponse.message = "get user "+ id +" info!";
		apiResponse.data = userBusiness.getUserById(id);
		return apiResponse;
	}
	
    @GetMapping("/{id}/streams/{offset}/{limit}")
    public ApiResponse getStreams(@PathVariable(name = "id") int id
            , @PathVariable(name = "offset") int offset
            , @PathVariable(name = "limit") int limit) {
        if (offset <= 0)
            offset = 0;
        if (limit <= 0)
            limit = 5;
        ApiResponse response = new ApiResponse();
        User requestedUser = userBusiness.getUserById(id);
        if (requestedUser == null) {
            response.statusCode = 400;
            response.message = "Invalid Params!";
            response.errorCode = -1;
            return response;
        }
        List<Stream> requestedData = new ArrayList<>(limit);
        List<Stream> currentData = requestedUser.getStreams();
        int startLength = offset - 1;
        if (startLength > currentData.size()) {
            requestedData = null;
        } else {
            for (int i = startLength, picked = 0; i < currentData.size(); i++, picked++) {
                if (picked < limit) {
                    requestedData.add(currentData.get(i));
                }
            }
        }
        response.statusCode = 200;
        response.message = "Success!";
        response.data = requestedData;
        return response;
    }

    @GetMapping("/auth/notification")
    public ApiResponse getNotification(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get user's notification!";
        apiResponse.data = userBusiness.getNotification(user.getUserId());
        return apiResponse;
    }

    @PostMapping("/auth/notification/delete")
    public ApiResponse deleteNotification(@RequestBody Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        System.out.println("======================== " + id + "===========================");
        try {
            userBusiness.deleteNotification(id);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.statusCode = 200;
            apiResponse.message = "delete user's notification!";
            return apiResponse;

        } catch (Exception e) {
            System.out.print(e);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.statusCode = 500;
            apiResponse.message = "delete user's notification failed!";
            return apiResponse;
        }

    }
}
