package com.example.denis.mytvshow.utils;

import com.example.denis.mytvshow.data.RootObject;
import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.ShowImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsitec205.ferreira on 03/08/17.
 */

public class JsonUtils {

    public static List<Serie> getFromJson(String resultJson) throws JSONException {

        Gson gson = new Gson();
        RootObject rootObject = null;
        if(resultJson != null) {
            Type collectionType = new TypeToken<RootObject>(){}.getType();
            rootObject = gson.fromJson(resultJson, collectionType);
        }
        return rootObject != null ? rootObject.getResults() : null;
    }

    public static Serie getDetailFromJson(String resultJson) throws JSONException {

        Gson gson = new Gson();
        Serie rootObject = null;
        if(resultJson != null) {
            Type collectionType = new TypeToken<Serie>(){}.getType();
            rootObject = gson.fromJson(resultJson, collectionType);
        }
        return rootObject;
    }

    public static ShowImage getShowImagesFromJson(String resultJson) throws JSONException {

        Gson gson = new Gson();
        ShowImage rootObject = null;
        if(resultJson != null) {
            Type collectionType = new TypeToken<ShowImage>(){}.getType();
            rootObject = gson.fromJson(resultJson, collectionType);
        }
        return rootObject;
    }
}
