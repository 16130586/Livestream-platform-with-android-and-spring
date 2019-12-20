package com.t4.androidclient.model.helper;

import com.t4.androidclient.model.livestream.Genre;
import com.t4.androidclient.model.livestream.StreamType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StreamTypeHelper {
    public static List<StreamType> parseGenreJson(String jsonString) {
        ArrayList<StreamType> typeList = new ArrayList<>();
        try {
                JSONObject jsonObject = new JSONObject(jsonString);
                 if (jsonObject.getInt("statusCode") == 200) {
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonType = data.getJSONObject(i);
                    int id = jsonType.getInt("typeId");
                    String typeName = jsonType.getString("typeName");
                    int numberOfType = jsonType.getInt("numberOfType");
                    StreamType type = new StreamType(id, typeName);
                    type.setNumberOfType(numberOfType);
                    typeList.add(type);
                    }
                    return typeList;
                }else{
                     return null;
                 }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
