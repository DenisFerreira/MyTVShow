package com.example.denis.mytvshow.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.denis.mytvshow.adapters.EndlessRecyclerViewScrollListener;
import com.example.denis.mytvshow.adapters.SerieAdapter;
import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.SerieDatabaseInfo;
import com.example.denis.mytvshow.loaders.SeriesLoader;
import com.ferreira.denis.mytvshow.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Series. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SerieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class SerieListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final int ID_LOADER = 25;
    private static final int ID_ADD_DATA_LOADER = 26;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private SerieAdapter mAdapter;
    private String mLocalQuery;
    private Parcelable layoutManagerSavedState;
    private static final String SAVED_LAYOUT_MANAGER = "saved_layout_manager";
    private static final String EXTRA_DISPLAY_TYPE = "display_type";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ValueEventListener searchEvent;
    private Map<String, SerieDatabaseInfo> serieInfolist;
    private SerieListActivity myClassReference = this;

    private GridLayoutManager layoutManager;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_list);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("favorites")
                .child(mAuth.getCurrentUser().getUid());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.serie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mRecyclerView = findViewById(R.id.serie_list);
        assert mRecyclerView != null;
        layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new SerieAdapter(this, mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

        scrollListener  = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Loader loader = getSupportLoaderManager().getLoader(ID_ADD_DATA_LOADER);
                Bundle args = new Bundle();
                args.putInt(SeriesLoader.PAGE_ARGUMENT_KEY, page+1);
                if(mLocalQuery == null)
                    args.putInt(SeriesLoader.LOADER_ARGUMENT_KEY, SeriesLoader.POPULAR_ARGUMENT);
                else {
                    args.putInt(SeriesLoader.LOADER_ARGUMENT_KEY, SeriesLoader.SEARCH_ARGUMENT);
                    args.putString(SeriesLoader.QUERY_ARGUMENT, mLocalQuery);
                }
                if(loader == null)
                    getSupportLoaderManager().initLoader(ID_ADD_DATA_LOADER, args, myClassReference);
                else
                    getSupportLoaderManager().restartLoader(ID_ADD_DATA_LOADER, args, myClassReference);
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        if(savedInstanceState != null) {
            layoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
            mLocalQuery = savedInstanceState.getString(EXTRA_DISPLAY_TYPE);
        }
        handleIntent(getIntent());

        AdView mAdView = findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            loadData(query);
            return;
        }

        if(intent.hasExtra(SerieDetailFragment.ARG_ITEM_ID) && mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(SerieDetailFragment.ARG_ITEM_ID, intent.getIntExtra(SerieDetailFragment.ARG_ITEM_ID, 0));
            SerieDetailFragment fragment = new SerieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.serie_detail_container, fragment)
                    .commit();
            mLocalQuery = intent.getStringExtra(SerieDetailFragment.ARG_QUERY);
        }
        loadData(mLocalQuery);

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int widthDivider = 500;
        int nColumns = width / widthDivider;
        if (nColumns < 2 || mTwoPane) return 2;
        return nColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.isEmpty())
                    loadData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            case R.id.popular_menu:
                loadData(null);
                getIntent().removeExtra(SerieDetailFragment.ARG_QUERY);
                getIntent().removeExtra(SerieDetailFragment.ARG_ITEM_ID);
                mRecyclerView.addOnScrollListener(scrollListener);
                break;
            case R.id.favorite_menu:
                getFavorites();
                mRecyclerView.removeOnScrollListener(scrollListener);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFavorites(){

        searchEvent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String,SerieDatabaseInfo>> typeIndicator =
                        new GenericTypeIndicator<Map<String,SerieDatabaseInfo>>() {};
                if(dataSnapshot.getValue(typeIndicator) != null) {
                    serieInfolist = dataSnapshot.getValue(typeIndicator);
                    List<Serie> list = new ArrayList<>();
                    for (Map.Entry<String, SerieDatabaseInfo> entry:serieInfolist.entrySet()){
                        if(entry.getValue().isFavorite()){
                            Serie serie = new Serie();
                            serie.setId(entry.getValue().getId());
                            serie.setName(entry.getValue().getName());
                            serie.setPosterPath(entry.getValue().getPosterPath());
                            list.add(serie);
                        }
                    }
                    updateAdapterData(list);
                }else
                    Snackbar.make(mRecyclerView, getString(R.string.no_favorites), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addListenerForSingleValueEvent(searchEvent);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_LOADER:
            case ID_ADD_DATA_LOADER:
                return new SeriesLoader(this, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader != null)
            switch (loader.getId()) {
                case ID_LOADER:
                    if (data != null)
                        updateAdapterData((List<Serie>) data);
                    break;
                case ID_ADD_DATA_LOADER:
                    if (data != null)
                        addMoreAdapterData((List<Serie>) data);
                    break;
            }
    }

    private void updateAdapterData(List<Serie> data) {
        mAdapter.setData(data, mLocalQuery);
        restoreLayoutManagerPosition();
    }

    private void addMoreAdapterData(List<Serie> data) {
        mAdapter.addData(data, mLocalQuery);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void loadData(String query) {
        mLocalQuery = query;
        Loader loader = getSupportLoaderManager().getLoader(ID_LOADER);
        Bundle args = new Bundle();
        if(query == null)
            args.putInt(SeriesLoader.LOADER_ARGUMENT_KEY, SeriesLoader.POPULAR_ARGUMENT);
        else {
            args.putInt(SeriesLoader.LOADER_ARGUMENT_KEY, SeriesLoader.SEARCH_ARGUMENT);
            args.putString(SeriesLoader.QUERY_ARGUMENT, query);
        }
        if(loader == null)
            getSupportLoaderManager().initLoader(ID_LOADER, args, this);
        else
            getSupportLoaderManager().restartLoader(ID_LOADER, args, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        layoutManagerSavedState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVED_LAYOUT_MANAGER, layoutManagerSavedState);
        outState.putString(EXTRA_DISPLAY_TYPE, mLocalQuery);
        super.onSaveInstanceState(outState);
    }

    private void restoreLayoutManagerPosition() {
        if (layoutManagerSavedState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }
}
