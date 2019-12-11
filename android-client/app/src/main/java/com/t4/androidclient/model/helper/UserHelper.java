package com.t4.androidclient.model.helper;

import com.t4.androidclient.model.livestream.User;

import org.json.JSONObject;

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
            user.setUsername(jsonObject.getString("userName"));
            user.setNickname(jsonObject.getString("nickName"));
            user.setAvatar(jsonObject.getString("avatar"));
            user.setGmail(jsonObject.getString("gmail"));
            user.setSubscribeTotal(jsonObject.getInt("subscribeTotal"));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
