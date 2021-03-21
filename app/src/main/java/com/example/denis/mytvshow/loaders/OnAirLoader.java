package com.example.denis.mytvshow.loaders;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.RemoteViews;

import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.SerieDatabaseInfo;
import com.example.denis.mytvshow.utils.JsonUtils;
import com.example.denis.mytvshow.utils.NetworkUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lsitec205.ferreira on 24/01/18.
 */

public class OnAirLoader extends AsyncTask<Void, Void, List<Serie>> {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ValueEventListener searchEvent;
    private Map<String, SerieDatabaseInfo> serieInfolist;
    private OnAirInterface mOnAirInterface;

    Context mContext;

    @Override
    protected List<Serie> doInBackground(Void... voids) {
        List<Serie> listResult = new ArrayList<>();
        for (Map.Entry<String, SerieDatabaseInfo> entry:serieInfolist.entrySet()){
            if(entry.getValue().isFavorite()){
                int id = entry.getValue().getId();
                String first_air_date = entry.getValue().getFirstAirDate();
                try{
                    String result = NetworkUtils.getResponseFromHttpUrl(
                            NetworkUtils.buildIsOnAirUrl(mContext, first_air_date));
                    List<Serie> tempList = JsonUtils.getFromJson(result);
                    if(tempList != null)
                        for(Serie serie:tempList) {
                            if(serie.getId() == id) {
                                listResult.add(serie);
                                break;
                            }
                        }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listResult;
    }

    public interface OnAirInterface {
        void handleResult(List<Serie> serieListResult);
    }

    public OnAirLoader(Context context, Map<String, SerieDatabaseInfo> data, OnAirInterface airInterface) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("favorites")
                .child(mAuth.getCurrentUser().getUid());
        mOnAirInterface = airInterface;
        serieInfolist = data;
    }

    @Override
    protected void onPostExecute(List<Serie> series) {
        mOnAirInterface.handleResult(series);
        super.onPostExecute(series);
    }

}
