package com.t4.androidclient.model.helper;

import com.t4.androidclient.model.livestream.LiveStream;
import com.t4.androidclient.model.livestream.User;

import org.json.JSONObject;

import java.util.Map;

public class LiveStreamHelper {
    public static LiveStream parse(Map<String, Object> rawData) {
        LiveStream ret = new LiveStream();
        try {
            JSONObject d = new JSONObject(rawData);
            int streamStatus = d.getInt("status");
            ret.setStreamId(d.getInt("streamId"));
            ret.setTitle(d.getString("title"));
            ret.setThumbnail(d.getString("thumbnail"));
            ret.setStatus(streamStatus);

            if (d.has("owner")) {
                User owner = UserHelper.parseUserJson(d.getJSONObject("owner"));
                if(owner != null)
                    ret.setOwner(owner);
            }
            // con thieu start date
            if (streamStatus < 0) {
                // map cho dung voi stream ended == video
                ret.setStoredUrl(d.getString("storedUrl"));
            } else {
                // map cho dung voi live stream
                ret.setPrimaryServerURL(d.getString("primaryServerURL"));
                ret.setApplication(d.getString("application"));
                ret.setStreamName(d.getString("streamName"));
                ret.setHostPort(d.getInt("hostPort"));
            }

            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
