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
import java.util.*;

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
		apiResponse.message = "get all stream-genre of user";
		List<StreamType> listRequest = streamTypeBusiness.listStreamTypeByUserID(userId);
		Set<Integer> setID = new TreeSet<Integer>();
		for (StreamType type : listRequest){
			setID.add(type.getTypeId());
		}
		List<Integer> listID = new ArrayList<Integer>(setID);
		List<StreamType> listNew = new ArrayList<>(listID.size());
		for(int i=0;i<listID.size();i++){
			listNew.add(new StreamType());
		}
		for (StreamType type : listRequest){
			int typeID = type.getTypeId();
			for (int index=0;index<listID.size();index++){
				if(typeID==listID.get(index)){ //1-2-3-10
						type.setNumberOfType(listNew.get(index).getNumberOfType() + 1);
						listNew.set(index, type);
				}
			}
		}
		apiResponse.data = listNew;
		//apiResponse.data = streamTypeBusiness.listStreamAndTypeByUserID(userId);
		return apiResponse;
	}
}




