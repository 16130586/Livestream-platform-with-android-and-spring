package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.ReportOnOwnerStreamBusiness;
import com.t4.LiveServer.jwt.JwtProvider;
import com.t4.LiveServer.model.security.CustomUserDetails;
import com.t4.LiveServer.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class ProfitController {
    @Value("${host.static}")
    private String hostStatic;

    @Autowired
    ReportOnOwnerStreamBusiness reportBusiness;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("profit/{userId}/index")
    public String index(HttpServletRequest request, @PathVariable int userId) {
        String token = getJwtFromRequest(request);
        if (null == token || !checkValidToken(token, userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Page not found"
            );
        }
        LocalDate currenVnesDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalDate startDate = null, endDate = null;
        // start validation logic of start and end date
        // default choice
        startDate = endDate = currenVnesDate;
        endDate = currenVnesDate;
        String rawEndDate = endDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        startDate = endDate;
        String rawStartDate = startDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));

        String userName = jwtProvider.getUserNameFromJWT(token);
        // Lấy thông tin người dùng từ username
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        request.setAttribute("userId" , userDetails.getUser().getUserId());
        request.setAttribute("startDate", rawStartDate);
        request.setAttribute("endDate", rawEndDate);
        request.setAttribute("hiddenToken", request.getParameter("token"));
        request.setAttribute("host-static", hostStatic);
        return "profit_index";
    }

    @GetMapping("profit/{userId}/payment")
    public String payment(HttpServletRequest request, @PathVariable int userId) {
        String token = getJwtFromRequest(request);
        if (null == token || !checkValidToken(token, userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Page not found"
            );

        }
        String rawMonth = request.getParameter("month");
        String rawYear = request.getParameter("year");
        LocalDate currenVnesDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        int month, year;
        if(rawMonth != null && rawYear != null){
            month = Integer.parseInt(rawMonth);
            year = Integer.parseInt(rawYear);
        }else {

            month = currenVnesDate.getMonthValue();
            year = currenVnesDate.getYear();
        }


        String userName = jwtProvider.getUserNameFromJWT(token);
        // Lấy thông tin người dùng từ username
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        double profitRequested = reportBusiness.getProfit(month , year , userId);
        currenVnesDate = LocalDate.of(year , month , 1);
        request.setAttribute("userId" , userDetails.getUser().getUserId());
        request.setAttribute("requestedDate", currenVnesDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        request.setAttribute("hiddenToken", request.getParameter("token"));
        request.setAttribute("host-static", hostStatic);
        request.setAttribute("profit" , profitRequested);
        return "profit_payment";
    }

    private boolean checkValidToken(String token, int userId) {
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
            // Lấy username từ chuỗi jwt
            String userName = jwtProvider.getUserNameFromJWT(token);
            // Lấy thông tin người dùng từ username
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            // Set attribute user into httpRequest
            if (userDetails != null && userDetails.getUser() != null)
                return userId == userDetails.getUser().getUserId();
        } else {
            return false;
        }
        return false;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getParameter("token");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }


}
