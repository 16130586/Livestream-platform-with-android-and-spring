package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.ReportOnOwnerStreamBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.core.JsonHelper;
import com.t4.LiveServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
public class ProfitRestController {
    @Autowired
    ReportOnOwnerStreamBusiness reportBusiness;
    @GetMapping("profit/hello")
    public ApiResponse asd(){
        return new ApiResponse();
    }
    @GetMapping("auth/profit/count/{action}")
    public ApiResponse getCount(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable String action) throws IOException {
        ApiResponse responseFormat = new ApiResponse();
        if (null == request.getAttribute("user")) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Page not found"
            );
        }
        try {
            LocalDate currenVnesDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            int day = currenVnesDate.getDayOfMonth();
            int month = currenVnesDate.getMonthValue();
            int year = currenVnesDate.getYear();
            User user = (User) request.getAttribute("user");
            long count = -1;
            if ("view".equals(action)) {
                count = reportBusiness.viewCounts(day, month, year, user.getUserId());
            }
            if ("like".equals(action)) {
                count = reportBusiness.likeCounts(day, month, year, user.getUserId());
            }
            if ("comment".equals(action)) {
                count = reportBusiness.commentCounts(day, month, year, user.getUserId());
            }
            if ("report".equals(action)) {
                count = reportBusiness.reportCounts(day, month, year, user.getUserId());
            }
            responseFormat.message = "OK";
            responseFormat.errorCode = 0;
            responseFormat.data = count;
        } catch (Exception e) {
            responseFormat.message = e.getMessage();
            responseFormat.statusCode = 500;
            responseFormat.errorCode = -1;
        }
        return responseFormat;

    }
    @GetMapping("auth/profit")
    public ApiResponse getProfit(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable String action) throws IOException {
        ApiResponse responseFormat = new ApiResponse();
        if (null == request.getAttribute("user")) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Page not found"
            );
        }
        try {
            LocalDate currenVnesDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            int day = currenVnesDate.getDayOfMonth();
            int month = currenVnesDate.getMonthValue();
            int year = currenVnesDate.getYear();
            User user = (User) request.getAttribute("user");
            long count = -1;
            if ("view".equals(action)) {
                count = reportBusiness.viewCounts(day, month, year, user.getUserId());
            }
            if ("like".equals(action)) {
                count = reportBusiness.likeCounts(day, month, year, user.getUserId());
            }
            if ("comment".equals(action)) {
                count = reportBusiness.commentCounts(day, month, year, user.getUserId());
            }
            if ("report".equals(action)) {
                count = reportBusiness.reportCounts(day, month, year, user.getUserId());
            }
            responseFormat.message = "OK";
            responseFormat.errorCode = 0;
            responseFormat.data = count;
        } catch (Exception e) {
            responseFormat.message = e.getMessage();
            responseFormat.statusCode = 500;
            responseFormat.errorCode = -1;
        }
        return responseFormat;

    }
}
