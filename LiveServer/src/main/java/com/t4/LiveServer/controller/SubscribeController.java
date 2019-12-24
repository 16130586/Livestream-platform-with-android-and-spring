package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.SubscribeBusiness;
import com.t4.LiveServer.core.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/channel")

public class SubscribeController {
	
	@Autowired
	SubscribeBusiness subscribeBusiness;
	
	@PostMapping("/subscribe")
	public ApiResponse subscribe(@RequestBody Map<String, String> userIDs) {
		ApiResponse response = new ApiResponse();
		response.statusCode = 200;
		response.message = "Subscribe success!";
		response.data = subscribeBusiness.doSubscribe(Integer.parseInt(userIDs.get("subscriberID")),Integer.parseInt(userIDs.get("publisherID")));
		return response;
	}
	
	@PostMapping("/unsubscribe")
	public ApiResponse unsubscribe(@RequestBody Map<String, String> userIDs) {
		ApiResponse response = new ApiResponse();
		response.statusCode = 200;
		response.message = "Subscribe success!";
		response.data = subscribeBusiness.undoSubscribe(Integer.parseInt(userIDs.get("subscriberID")),Integer.parseInt(userIDs.get("publisherID")));
		return response;
	}
	
	@PostMapping("/checksubscribe")
	public ApiResponse checksubscribe(@RequestBody Map<String, String> userIDs) {
		ApiResponse response = new ApiResponse();
		response.statusCode = 200;
		response.message = "Subscribe success!";
		response.data = subscribeBusiness.checkSubscribe(Integer.parseInt(userIDs.get("subscriberID")),Integer.parseInt(userIDs.get("publisherID")));
		return response;
	}
}
