package com.t4.androidclient.model.livestream;

import org.json.JSONObject;

public class UserHelper {

    public static User parseUserJson(String jsonData) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.getInt("statusCode") == 200) {
                JSONObject userData = jsonObject.getJSONObject("data");
                user.setUsername(userData.getString("userName"));
                user.setNickname(userData.getString("nickName"));
                user.setAvatar(userData.getString("avatar"));
                user.setGmail(userData.getString("gmail"));
                user.setSubscribeTotal(userData.getInt("subscribeTotal"));
                // TODO get notification, favourite type, favourite saved, subscription, streams (if necessary)
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
