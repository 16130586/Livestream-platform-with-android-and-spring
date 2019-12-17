package com.t4.androidclient.model.helper;

import com.t4.androidclient.model.livestream.Genre;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GenreHelper {

    public static List<String> parseGenreJson(String jsonString) {
        ArrayList<String> genreList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++){
                JSONObject o = data.getJSONObject(i);
                int id = o.getInt("typeId");
                String genreString = o.getString("typeName");
                Genre genre = new Genre(id, genreString);
                genreList.add(genre.getGenreString());
            }
            return genreList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
