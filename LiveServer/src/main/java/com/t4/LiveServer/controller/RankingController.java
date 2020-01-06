package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@Controller
public class RankingController {

    @Autowired
    UserBusiness userBusiness;

    @GetMapping("/ranks/buy/{userId}")
    public String getRankingInfo(@PathVariable String userId) {
        return "ranking_info";
    }

    @GetMapping("/ranks/top")
    @ResponseBody
    public ApiResponse getTopRankingUser() {
        Calendar calendar = Calendar.getInstance();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get top ranking user";
        apiResponse.data = userBusiness.getTopRankingUser(calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.YEAR));
        return apiResponse;
    }

}
