package com.example.denis.mytvshow.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.ShowImage;
import com.example.denis.mytvshow.utils.JsonUtils;
import com.example.denis.mytvshow.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by lsitec205.ferreira on 24/01/18.
 */

public class ImageLoader extends AsyncTaskLoader<ShowImage> {

    public static final String SERIE_ID_ARGUMENT = "tv_id";
    private ShowImage mLastResult;
    private Bundle mBundle;

    Context mContext;

    public ImageLoader(Context context, Bundle args) {
        super(context);
        mContext = context;
        mBundle = args;
    }

    @Override
    public ShowImage loadInBackground() {
        try {
            String json;
            ShowImage showImage;
            int id = mBundle.getInt(SERIE_ID_ARGUMENT);
            json = NetworkUtils.getResponseFromHttpUrl(
                    NetworkUtils.buildImagesUrl(mContext, id));
            showImage = JsonUtils.getShowImagesFromJson(json);
            return showImage;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(ShowImage data) {
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
