package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.StreamTypeBusiness;
import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.model.StreamType;
import com.t4.LiveServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/streamtype")

public class StreamTypeController {

    @Autowired
	StreamTypeBusiness streamTypeBusiness;

    @GetMapping("/listByUserID/{userID}")
    public ApiResponse getStreamTypeByUserID(@PathVariable(name = "userID") int userId) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.statusCode = 200;
		apiResponse.message = "get all genre of user";
		apiResponse.data = streamTypeBusiness.listStreamTypeByUserID(userId);
		System.out.println("Loi roi");
		return apiResponse;
	}
}




