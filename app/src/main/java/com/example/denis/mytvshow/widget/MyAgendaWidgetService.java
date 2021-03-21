package com.example.denis.mytvshow.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.SerieDatabaseInfo;
import com.example.denis.mytvshow.loaders.OnAirLoader;
import com.ferreira.denis.mytvshow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MyAgendaWidgetService extends RemoteViewsService {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ValueEventListener searchEvent;
    private Map<String, SerieDatabaseInfo> serieInfolist;

    private class MyRemoteViewFactory implements RemoteViewsFactory , OnAirLoader.OnAirInterface{

        private List<Serie> data = null;
        private Context mContext;
        private CountDownLatch mCountDownLatch;
        private OnAirLoader.OnAirInterface mInterface;
        private FirebaseUser mUser;

        private MyRemoteViewFactory(Context context) {
            mContext = context;
            mInterface = this;
        }

        @Override
        public void onCreate() {
            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            mUser = mAuth.getCurrentUser();
            if(mUser != null)
                myRef = database.getReference("favorites")
                    .child(mUser.getUid());

        }

        @Override
        public void onDataSetChanged() {
            if(mUser!= null) {
                mCountDownLatch = new CountDownLatch(1);
                getFavorites();

                try {
                    mCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void getFavorites() {

            searchEvent = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<Map<String, SerieDatabaseInfo>> typeIndicator =
                            new GenericTypeIndicator<Map<String, SerieDatabaseInfo>>() {
                            };
                    if (dataSnapshot.getValue(typeIndicator) != null) {
                        serieInfolist = dataSnapshot.getValue(typeIndicator);
                        List<Serie> list = new ArrayList<>();
                        for (Map.Entry<String, SerieDatabaseInfo> entry : serieInfolist.entrySet()) {
                            if (entry.getValue().isFavorite()) {
                                Serie serie = new Serie();
                                serie.setId(entry.getValue().getId());
                                serie.setName(entry.getValue().getName());
                                serie.setPosterPath(entry.getValue().getPosterPath());
                                list.add(serie);
                            }
                        }
                        new OnAirLoader(mContext, serieInfolist, mInterface).execute();

                    }
                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }

            };
            myRef.addListenerForSingleValueEvent(searchEvent);
        }


            @Override
            public void onDestroy () {
                if (data != null) {
                    data = null;
                }
            }

            @Override
            public int getCount () {
                return data == null ? 0 : data.size();
            }

            @Override
            public RemoteViews getViewAt ( int i){
                RemoteViews views;
                views = new RemoteViews(mContext.getPackageName(), R.layout.myagenda_widget_list);
                if (i == AdapterView.INVALID_POSITION || data == null || data.get(i) == null) {
                    return views;
                }
                String result = data.get(i).getName();

                views.setTextViewText(R.id.appwidget_list_item_text, result);

                return views;
            }

            @Override
            public RemoteViews getLoadingView () {
                return null;
            }

            @Override
            public int getViewTypeCount () {
                return 1;
            }

            @Override
            public long getItemId ( int position){
                return data.get(position).getId();
            }

            @Override
            public boolean hasStableIds () {
                return true;
            }

        @Override
        public void handleResult(List<Serie> serieListResult) {
            if (mCountDownLatch.getCount() == 0) {
                // Item changed externally. Initiate refresh.
                Intent updateWidgetIntent = new Intent(mContext,
                        MyAgenda.class);
                updateWidgetIntent.setAction(
                        MyAgenda.ACTION_DATA_UPDATED);
                mContext.sendBroadcast(updateWidgetIntent);
            } else
                data = serieListResult;
            mCountDownLatch.countDown();
        }
    }

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new MyRemoteViewFactory(this.getApplicationContext());
        }

    }

