package com.t4.LiveServer.controller;

import com.t4.LiveServer.business.interfaze.StreamBusiness;
import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.core.ApiResponse;
import com.t4.LiveServer.entryParam.base.Stream.CreatingStreamEntryParams;
import com.t4.LiveServer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/streams"})
public class StreamController {

    @Autowired
    StreamBusiness streamBusiness;

    @Autowired
    UserBusiness userBusiness;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreatingStreamEntryParams entryParams) {
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

    @PostMapping("/{id}/start")
    public ApiResponse start(@PathVariable("id") String id) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Object rs = streamBusiness.start(id);
            if (rs != null) {
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

    @PostMapping("/{id}/stop")
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

    @GetMapping("/recommend/{offset}/{pageSize}")
    public ApiResponse getRecommendForUser(@PathVariable Integer offset, @PathVariable Integer pageSize, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get recommend live stream!";
        apiResponse.data = streamBusiness.getRecommendForUser(user.getUserId(), offset, pageSize);

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

    @GetMapping("/find/{streamName}")
    public ApiResponse getStreamsByName(@PathVariable String streamName) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by name";
        apiResponse.data = streamBusiness.getStreamsByName(streamName);

        return apiResponse;
    }

    @GetMapping("/findAdvance/{streamName}")
    public ApiResponse getStreamsByNameAndType(@PathVariable String streamName, @RequestBody Map<String, String> datas) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by name and type";
        List<StreamType> streamTypes = new ArrayList<>();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            streamTypes.add((StreamType) streamBusiness.getGenreByName(entry.getValue()));
        }
        apiResponse.data = streamBusiness.getStreamsByNameAndType(streamName, streamTypes);

        return apiResponse;
    }

    @GetMapping("/findAdvance")
    public ApiResponse getStreamsByType(@RequestBody Map<String, String> datas) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 200;
        apiResponse.message = "get stream by type";
        List<StreamType> streamTypes = new ArrayList<>();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            streamTypes.add((StreamType) streamBusiness.getGenreByName(entry.getValue()));
        }
        apiResponse.data = streamBusiness.getStreamsByNameAndType(null, streamTypes);

        return apiResponse;
    }

    @PostMapping("/{streamId}/comment")
    public ApiResponse comment(@RequestBody Map<String, String> datas, @PathVariable Integer streamId, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.statusCode = 201;
        apiResponse.message = "User comment to live stream";
        Comment comment = new Comment();
        comment.setOwnerId(user.getUserId());
        comment.setStreamId(streamId);
        comment.setMessage(datas.get("message"));
        comment.setStreamStatus(Integer.parseInt(datas.get("streamStatus")));
        comment.setVideoTime(Integer.parseInt(datas.get("videoTime")));
        comment.setCommentSource(CommentSource.INTERNAL);
        comment.setCreateTime(new Date());
        apiResponse.data = streamBusiness.saveComment(comment);

        return apiResponse;
    }
}
