package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping({"/streams"})
public class StreamController {

    @Autowired
    StreamBusiness streamBusiness;

    @Autowired
    UserBusiness userBusiness;

    @Autowired
    MailBusiness mailBusiness;

    @PostMapping("/auth/create")
    public ApiResponse create(@RequestBody CreatingStreamEntryParams entryParams, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        entryParams.userId = user.getUserId();
        ApiResponse apiResponse = new ApiResponse();
        try {
            Object rs = streamBusiness.create(entryParams);
            apiResponse.statusCode = 200;
            apiResponse.message = "Ok";
            apiResponse.data = rs;
        } catch (Exception e) {
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }

    @PostMapping("/auth/{id}/start")
    public ApiResponse start(@PathVariable("id") String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Stream rs = streamBusiness.start(id);
            if (rs != null && rs.getStatus() > 0) {
                apiResponse.statusCode = 200;
                apiResponse.message = "Ok";
                apiResponse.data = rs;
            }else {
                apiResponse.statusCode = 400;
                apiResponse.message = "Not started!";
            }
        } catch (Exception e) {
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }

    @PostMapping("/auth/{id}/stop")
    public ApiResponse stop(@PathVariable("id") String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Object rs = streamBusiness.stop(id);
            if (rs != null) {
                apiResponse.statusCode = 200;
                apiResponse.message = "Ok";
                apiResponse.data = rs;
            }else {
                apiResponse.statusCode = 400;
                apiResponse.message = "Not stopped!";
            }
        } catch (Exception e) {
            apiResponse.statusCode = 500;
            apiResponse.message = e.getMessage();
        }
        return apiResponse;
    }

    @GetMapping("/auth/recommend/{offset}/{pageSize}")
    public ApiResponse getRecommendForUser(@PathVariable Integer offset, @PathVariable Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get recommend live stream!";
        apiResponse.data = streamBusiness.getRecommendForUser(user.getUserId(), offset, pageSize);

        return apiResponse;
    }

    @GetMapping("/recommend/{offset}/{pageSize}")
    public ApiResponse getRecommendForCookieUser(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get recommend live stream!";
        apiResponse.data = streamBusiness.getRecommendForCookieUser(offset, pageSize);
        return apiResponse;
    }

    @GetMapping("/genre")
    public ApiResponse getAllGenre() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get all genre of stream";
        apiResponse.data = streamBusiness.getAllGenre();
        return apiResponse;
    }

    @GetMapping("/find/{streamName}/{offset}/{pageSize}")
    public ApiResponse getStreamsByName(@PathVariable String streamName, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by name";
        apiResponse.data = streamBusiness.getStreamsByName(streamName, offset, pageSize);
        return apiResponse;
    }

    @PostMapping("/findAdvance/{streamName}/{offset}/{pageSize}")
    public ApiResponse getStreamsByNameAndType(@PathVariable String streamName, @RequestBody List<String> datas, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by name and type";
        List<String> streamTypes = new ArrayList<>();
        for (String data : datas) {
            streamTypes.add(data);
        }
        apiResponse.data = streamBusiness.getStreamsByNameAndType(streamName, streamTypes, offset, pageSize);

        return apiResponse;
    }

    @PostMapping("/findAdvance/{offset}/{pageSize}")
    public ApiResponse getStreamsByType(@RequestBody List<String> datas, @PathVariable Integer offset, @PathVariable Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by type";
        List<String> streamTypes = new ArrayList<>();
        for (String data : datas) {
            streamTypes.add(data);
        }
        apiResponse.data = streamBusiness.getStreamsByNameAndType(null, streamTypes, offset, pageSize);

        return apiResponse;
    }

    @PostMapping("/auth/{streamId}/comment")
    public ApiResponse comment(@RequestBody Map<String, String> datas, @PathVariable Integer streamId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 201;
        apiResponse.message = "User comment to live stream";
        Comment comment = new Comment();
        comment.setOwnerId(user.getUserId());
        comment.setStreamId(streamId);
        comment.setMessage(datas.get("message"));
        comment.setOwnerName(datas.get("ownerName"));
        comment.setStreamStatus(Integer.parseInt(datas.get("streamStatus")));
        comment.setVideoTime(Integer.parseInt(datas.get("videoTime")));
        comment.setCommentSource(CommentSource.INTERNAL);
        comment.setCreateTime(new Date());
        apiResponse.data = streamBusiness.saveComment(comment);
        return apiResponse;
    }
    @GetMapping("/{streamId}/comments/{videoTime}")
    public ApiResponse getCommentByVideoTime(@PathVariable Integer streamId, @PathVariable Integer videoTime) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "List comment of time: " + videoTime;
        List<Comment> returns = streamBusiness.getCommentByVideoTime(streamId , videoTime);
        if(returns == null){
            apiResponse.statusCode = 404;
        }
        apiResponse.data = returns;
        return apiResponse;
    }
	@GetMapping("/listByUserType/{userID}/{typeID}/{offset}/{limit}")
	public ApiResponse getStreamsByTypeOfUser(@PathVariable(name = "userID") int userID,@PathVariable(name = "typeID") int typeID
	 , @PathVariable(name = "offset") int offset
	 , @PathVariable(name = "limit") int limit) {
		if (offset <= 0)
			offset = 0;
		if (limit <= 0)
			limit = 5;
		ApiResponse response = new ApiResponse();
		response.statusCode = 200;
		response.message = "get streams by type of user";
		List<Stream> requestedData = new ArrayList<>(limit);
		List<Stream> currentData = streamBusiness.listStreamByTypeOfUser(userID,typeID);
		int startLength = offset-1 ;
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
	
	@GetMapping("/listWatchedByUserID/{userID}/{offset}/{limit}")
	public ApiResponse listWatchedStreamsByUserID(@PathVariable(name = "userID") int userID
	 , @PathVariable(name = "offset") int offset
	 , @PathVariable(name = "limit") int limit) {
		if (offset <= 0)
			offset = 0;
		if (limit <= 0)
			limit = 5;
		ApiResponse response = new ApiResponse();
		response.statusCode = 200;
		response.message = "get watched streams by ID of user";
		List<Stream> requestedData = new ArrayList<>(limit);
		List<Stream> currentData = streamBusiness.getWatchedStreamsByUserID(userID);
		int startLength = offset-1 ;
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

    @GetMapping("/trend/{offset}/{pageSize}")
    public ApiResponse getTrendingStreams(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get trending streams";
        apiResponse.data = streamBusiness.getTrendingStreams(offset, pageSize);
        return apiResponse;
    }


    @PostMapping("/upView/{streamId}")
    public ApiResponse upView(@PathVariable Integer streamId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "increase stream's view";
        apiResponse.data = streamBusiness.upView(streamId);
        return apiResponse;
    }

    @PostMapping("/auth/{streamId}/like")
    public ApiResponse like(@RequestBody Map<String, String> datas, @PathVariable Integer streamId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 201;
        apiResponse.message = "User like to live stream";

        apiResponse.data = streamBusiness.likeStream(user.getUserId(), streamId);
        return apiResponse;
    }

    @PostMapping("/auth/{streamId}/dislike")
    public ApiResponse dislike(@RequestBody Map<String, String> datas, @PathVariable Integer streamId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 201;
        apiResponse.message = "User like to live stream";

        apiResponse.data = streamBusiness.dislikeStream(user.getUserId(), streamId);
        return apiResponse;
    }

    @PostMapping("/auth/like/")
    public ApiResponse getLikeStatus(@RequestBody Map<String, String> datas, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get trending streams";
        apiResponse.data = streamBusiness.getLikeStatus(user.getUserId(), Integer.parseInt(datas.get("streamId")));
        return apiResponse;
    }

    @PostMapping("/auth/report/")
    public ApiResponse reportLive(@RequestBody Map<String, String> data, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        int liveId = Integer.parseInt(data.get("liveId"));
        String reason = data.get("reason");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get trending streams";
        apiResponse.data = streamBusiness.reportStream(liveId, user.getUserId(), reason);

        return apiResponse;
    }
}
