package com.t4.androidclient.model.livestream;

import android.media.session.MediaSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TokenPermissionHelper {
    ArrayList<String> permissions;

    public TokenPermissionHelper() {
        permissions = new ArrayList<>();
        permissions.add("user_location");
        permissions.add("user_events");
        permissions.add("publish_video");
        permissions.add("manage_pages");
        permissions.add("pages_show_list");
        permissions.add("groups_access_member_info");
        permissions.add("public_profile");
    }

    public List<TokenPermission> parseTokenPermissionJson(String jsonString) {
        ArrayList<TokenPermission> tokenPermissionList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++){
                JSONObject o = data.getJSONObject(i);
                String permission = o.getString("permission");
                String status = o.getString("status");
                TokenPermission tokenPermission = new TokenPermission(permission, status);
                tokenPermissionList.add(tokenPermission);
            }
            System.out.println(tokenPermissionList.toString());
            return tokenPermissionList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkTokenPermissions(List<TokenPermission> tokenPermissionList) {
        for (int i = 0; i < tokenPermissionList.size(); i++) {
            TokenPermission tokenPermission = tokenPermissionList.get(i);
            System.out.println(tokenPermission.toString());
            if (this.permissions.contains(tokenPermission.getPermission()) && "granted".equals(tokenPermission.getStatus())){
                continue;
            } else {
                return true;
            }
        }
        return true;
    }
}
