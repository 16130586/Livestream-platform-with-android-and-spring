package com.t4.androidclient.model.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.model.livestream.UserList;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserHelper {

//    public static User parseUserJson(String jsonData) {
//        User user = new User();
//        try {
//            JSONObject jsonObject = new JSONObject(jsonData);
//            if (jsonObject.getInt("statusCode") == 200) {
//                JSONObject userData = jsonObject.getJSONObject("data");
//                user.setUsername(userData.getString("userName"));
//                user.setNickname(userData.getString("nickName"));
//                user.setAvatar(userData.getString("avatar"));
//                user.setGmail(userData.getString("gmail"));
//                user.setSubscribeTotal(userData.getInt("subscribeTotal"));
//                // TODO get notification, favourite type, favourite saved, subscription, streams (if necessary)
//                return user;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public static User parseUserJson(Map<String, Object> rawData) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(rawData);
            return parseUserJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User parseUserJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.setId(jsonObject.getInt("userId"));
            user.setUsername(jsonObject.getString("userName"));
            user.setNickname(jsonObject.getString("nickName"));
            user.setDescription(jsonObject.getString("description"));
            user.setGmail(jsonObject.getString("gmail"));
            user.setSubscribeTotal(jsonObject.getInt("subscribeTotal"));
            user.setAvatar(jsonObject.getString("avatar"));
            user.setIsActivated(jsonObject.getInt("isActivated"));
            user.setBackground(jsonObject.getString("background"));
            user.setIsPublisher(jsonObject.getInt("isPublisher"));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> parseUserJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            UserList userList = objectMapper.readValue(json, UserList.class);
            return userList.getUserList();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
