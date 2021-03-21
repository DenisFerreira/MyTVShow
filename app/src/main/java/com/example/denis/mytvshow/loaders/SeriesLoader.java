package com.example.denis.mytvshow.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.utils.JsonUtils;
import com.example.denis.mytvshow.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by lsitec205.ferreira on 24/01/18.
 */

public class SeriesLoader extends AsyncTaskLoader<List<Serie>> {

    public static final String LOADER_ARGUMENT_KEY = "loader_argument";
    private static final String SERIE_ID_ARGUMENT = "tv_id";
    public static final String QUERY_ARGUMENT = "search_query";
    public static final String PAGE_ARGUMENT_KEY = "page";
    private List<Serie> mLastResult;
    private Bundle mBundle;
    public static final int POPULAR_ARGUMENT = 101;
    public static final int SEARCH_ARGUMENT = 103;

    Context mContext;

    public SeriesLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        mBundle = args;
    }

    @Override
    public List<Serie> loadInBackground() {
        try {
            String json;
            List<Serie> series;
            int key = mBundle.getInt(LOADER_ARGUMENT_KEY);
            int page = mBundle.getInt(PAGE_ARGUMENT_KEY);
            switch (key){
                case POPULAR_ARGUMENT:
                    json = NetworkUtils.getResponseFromHttpUrl(
                            NetworkUtils.buildPopularSeriesUrl(mContext, page));
                    break;
                case SEARCH_ARGUMENT:
                    json = NetworkUtils.getResponseFromHttpUrl(
                            NetworkUtils.buildSearchUrl(mContext, mBundle.getString(QUERY_ARGUMENT), page));
                    break;
                default:
                    return null;
            }

            series = JsonUtils.getFromJson(json);
            return series;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(List<Serie> data) {
        mLastResult = data;
        super.deliverResult(data);

    }

    @Override
    protected void onStartLoading() {
        if (mLastResult != null) {
            deliverResult(mLastResult);
        } else {
            forceLoad();
        }
    }
}
