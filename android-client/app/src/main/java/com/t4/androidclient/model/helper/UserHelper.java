package com.t4.androidclient.model.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t4.androidclient.model.livestream.PaySubscription;
import com.t4.androidclient.model.livestream.Ranking;
import com.t4.androidclient.model.livestream.Subscription;
import com.t4.androidclient.model.livestream.User;
import com.t4.androidclient.model.livestream.UserList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            user.setBackground(jsonObject.getString("background"));
            // pay subscription
            JSONArray jsonPaySubscriptions = jsonObject.getJSONArray("paySubscriptions");
            List<PaySubscription> paySubscriptions = new ArrayList<>();
            PaySubscription paySubscription;
            Subscription subscription;
            for (int i = 0; i < jsonPaySubscriptions.length(); i++) {
                paySubscription = new PaySubscription();
                JSONObject jsonPaySubscription = jsonPaySubscriptions.getJSONObject(i);
                paySubscription.setId(jsonPaySubscription.getInt("id"));
                paySubscription.setAmount(jsonPaySubscription.getDouble("amount"));
                paySubscription.setEndTime(new Date(jsonPaySubscription.getLong("endTime")));
                paySubscription.setStartTime(new Date(jsonPaySubscription.getLong("startTime")));
                JSONObject jsSubscription = jsonPaySubscription.getJSONObject("subscription");
                subscription = new Subscription();
                subscription.setId(jsSubscription.getInt("id"));
                subscription.setName(jsSubscription.getString("name"));
                subscription.setType(jsSubscription.getInt("type"));
                paySubscription.setSubscription(subscription);
                paySubscriptions.add(paySubscription);
            }
            user.setPaySubscriptions(paySubscriptions);
            // ranking
            JSONArray rankingsJson = jsonObject.getJSONArray("rankings");
            List<Ranking> rankings = new ArrayList<>();
            Ranking ranking;
            if (rankingsJson != null || rankingsJson.length() != 0) {
                for (int i = 0; i < rankingsJson.length(); i++) {
                    ranking = new Ranking();
                    JSONObject rankingJson = rankingsJson.getJSONObject(i);
                    ranking.setId(rankingJson.getInt("id"));
                    ranking.setPoint(rankingJson.getInt("point"));
                    ranking.setMonth(rankingJson.getInt("month"));
                    ranking.setYear(rankingJson.getInt("year"));
                    rankings.add(ranking);
                }
            } else {
                Calendar calendar = Calendar.getInstance();
                ranking = new Ranking();
                ranking.setPoint(0);
                ranking.setYear(calendar.get(Calendar.YEAR));
                ranking.setMonth(calendar.get(Calendar.MONTH) + 1);
                rankings.add(ranking);
            }
            user.setRankings(rankings);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> parseListUserJson(String json) {
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
