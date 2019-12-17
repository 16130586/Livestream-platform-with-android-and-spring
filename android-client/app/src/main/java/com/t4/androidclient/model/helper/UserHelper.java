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
        try { // nó thiếu properties nào đó thôi, dđọc exception là biết thiếu cái gì, tự custom ra 1 hàm mới
            // m dùng postman, request lên server, nó trả về kết quả là json, r dựa vào đó build 1 cái method khác
            user.setId(jsonObject.getInt("userId"));
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
