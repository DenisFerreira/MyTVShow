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

/**
 * Created by lsitec205.ferreira on 24/01/18.
 */

public class DetailLoader extends AsyncTaskLoader<Serie> {

    public static final String SERIE_ID_ARGUMENT = "tv_id";
    private Serie mLastResult;
    private Bundle mBundle;

    Context mContext;

    public DetailLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        mBundle = args;
    }

    @Override
    public Serie loadInBackground() {
        try {
            String json;
            Serie series;
            int id = mBundle.getInt(SERIE_ID_ARGUMENT);
            json = NetworkUtils.getResponseFromHttpUrl(
                    NetworkUtils.buildDetailUrl(mContext, id));
            series = JsonUtils.getDetailFromJson(json);
            return series;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(Serie data) {
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
