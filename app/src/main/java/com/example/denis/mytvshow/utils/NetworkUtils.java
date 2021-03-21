/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.denis.mytvshow.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ferreira.denis.mytvshow.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    final static String APY_KEY_PARAM = "api_key";
    final static String PAGE_PARAM = "page";
    final static String LANGUAGE_PARAM = "language";
    final static String QUERY_PARAM = "query";
    private static final String GET_POPULAR_URL = "https://api.themoviedb.org/3/tv/popular";
    private static final String GET_TOP_RATED_URL = "https://api.themoviedb.org/3/tv/top_rated";
    private static final String GET_DETAIL_URL = "https://api.themoviedb.org/3/tv";
    private static final String IMAGE_W185_BASE_URL = "http://image.tmdb.org/t/p/w185//";
    private static final String SEARCH_BASE_URL = "https://api.themoviedb.org/3/search/tv";
    private static final String DISCOVER_BASE_URL = "https://api.themoviedb.org/3/discover/tv";

    private static final String mLanguage = Locale.getDefault().getLanguage();
    private static final String IMAGE_W300_BASE_URL = "http://image.tmdb.org/t/p/w300//";


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildPopularSeriesUrl(Context context, Integer page) {
        return buildUrl(context, GET_POPULAR_URL, page);
    }
    public static URL buildTopRatedSeriesUrl(Context context, Integer page) {
        return buildUrl(context, GET_TOP_RATED_URL, page);
    }
    public static URL buildDetailUrl(Context context, Integer id) {
        return buildUrl(context, id, null);
    }

    private static URL buildUrl(Context context, String url, Integer page) {

        Uri.Builder builder = Uri.parse(url).buildUpon()
                .appendQueryParameter(APY_KEY_PARAM, context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN));
        if(page != null && page != 0)
            builder.appendQueryParameter(PAGE_PARAM, String.valueOf(page));
        if(!mLanguage.isEmpty())
            builder.appendQueryParameter(LANGUAGE_PARAM, mLanguage);
        Uri builtUri = builder.build();
        URL result = null;
        try {
            result = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    private static URL buildUrl(Context context, Integer id, Integer page) {
        Uri.Builder builder = Uri.parse(GET_DETAIL_URL).buildUpon();
        URL result = null;
        try {
            if(id != null)
                builder.appendPath(String.valueOf(id));
            else throw new MalformedURLException("Missing ID param");
            if(page != null && page != 0)
                builder.appendQueryParameter(PAGE_PARAM, String.valueOf(page));
            if(!mLanguage.isEmpty())
                builder.appendQueryParameter(LANGUAGE_PARAM, mLanguage);
            builder.appendQueryParameter(APY_KEY_PARAM, context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            Uri builtUri = builder.build();

            result = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    public static String buildURLGetPoster(String path) {
        String result = IMAGE_W185_BASE_URL+path;
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    public static String buildURLGetBackdrop(String path) {
        String result = IMAGE_W300_BASE_URL+path;
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    public static URL buildSearchUrl(Context context, String query, Integer page) {
        Uri.Builder builder = Uri.parse(SEARCH_BASE_URL).buildUpon();
        URL result = null;
        try {
            if(query != null && !query.isEmpty())
                builder.appendQueryParameter(QUERY_PARAM, query);
            else throw new MalformedURLException("Missing query param");
            if(!mLanguage.isEmpty())
                builder.appendQueryParameter(LANGUAGE_PARAM, mLanguage);
            if(page != null && page != 0)
                builder.appendQueryParameter(PAGE_PARAM, String.valueOf(page));
            builder.appendQueryParameter(APY_KEY_PARAM, context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            Uri builtUri = builder.build();

            result = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    public static URL buildImagesUrl(Context context, Integer id) {
        Uri.Builder builder = Uri.parse(GET_DETAIL_URL).buildUpon();
        URL result = null;
        try {
            if(id != null)
                builder.appendPath(String.valueOf(id));
            else throw new MalformedURLException("Missing ID param");
            builder.appendPath("images");
            //if(!mLanguage.isEmpty())
            //    builder.appendQueryParameter(LANGUAGE_PARAM, mLanguage);
            builder.appendQueryParameter(APY_KEY_PARAM, context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            Uri builtUri = builder.build();

            result = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + result);
        return result;
    }

    public static URL buildIsOnAirUrl(Context context, String first_air_date) {
        Uri.Builder builder = Uri.parse(DISCOVER_BASE_URL).buildUpon();
        URL result = null;
        try {
            if(first_air_date != null && !first_air_date.isEmpty()) {
                builder.appendQueryParameter("first_air_date.gte", first_air_date);
                builder.appendQueryParameter("first_air_date.lte", first_air_date);
            }
            else throw new MalformedURLException("Missing query param");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            int noOfDays = 7; //i.e two weeks
            Calendar calendar = Calendar.getInstance();
            Date firstDate = new Date();
            final String formatFirstDate = format.format(firstDate);
            calendar.setTime(firstDate);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            Date lastDate = calendar.getTime();
            final String formatLastDate = format.format(lastDate);
            builder.appendQueryParameter("air_date.gte", formatFirstDate);
            builder.appendQueryParameter("air_date.lte", formatLastDate);
            if(!mLanguage.isEmpty())
                builder.appendQueryParameter(LANGUAGE_PARAM, mLanguage);
            builder.appendQueryParameter(APY_KEY_PARAM, context.getResources().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            Uri builtUri = builder.build();

            result = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + result);
        return result;
    }
}