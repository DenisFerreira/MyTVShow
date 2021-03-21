package com.example.denis.mytvshow.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.denis.mytvshow.data.Backdrop;
import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.data.SerieDatabaseInfo;
import com.example.denis.mytvshow.data.ShowImage;
import com.example.denis.mytvshow.loaders.DetailLoader;
import com.example.denis.mytvshow.loaders.ImageLoader;
import com.example.denis.mytvshow.utils.NetworkUtils;
import com.ferreira.denis.mytvshow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


/**
 * A fragment representing a single Serie detail screen.
 * This fragment is either contained in a {@link SerieListActivity}
 * in two-pane mode (on tablets) or a {@link SerieDetailActivity}
 * on handsets.
 */
public class SerieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final int ID_LOADER_DETAIL = 32;
    private static final int ID_LOADER_IMAGES = 33;
    public static final String ARG_QUERY = "query";
    private int mTVId;
    private CollapsingToolbarLayout mAppBarLayout;
    private Serie mSerie;
    private ShowImage mShowImage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Context mContext;
    private ValueEventListener searchEvent;

    @Nullable
    @BindView(R.id.banner_detail) ImageView mBanner;
    @BindView(R.id.date_detail) TextView mFirstDate;
    @BindView(R.id.description_detail) TextView mDescription;
    @BindView(R.id.name_detail)  TextView mName;
    @BindView(R.id.number_of_seasons_detail) TextView mNumberOfSeasons;
    @BindView(R.id.pb_loading_indicator) ProgressBar mProgressBar;
    @BindView(R.id.container_detail) ConstraintLayout mLayout;
    @BindView(R.id.image_slider) SliderLayout mSliderLayout;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.rating_detail) TextView mRating;
    private Boolean isFavorite;

    @OnClick(R.id.fab)
    public void clickFab(View view){
        //Snackbar.make(view, "This Action will favorite a tv show", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();

        // Write a message to the database
        SerieDatabaseInfo info =
                new SerieDatabaseInfo(mSerie.getId(), mSerie.getName(),
                        mSerie.getPosterPath(), false, mSerie.getFirstAirDate());
        if(isFavorite == null || !isFavorite)
            info.setFavorite(true);
        myRef.setValue(info);

    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SerieDetailFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mTVId = getArguments().getInt(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            mAppBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            mContext = getContext();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.serie_detail, container, false);

        ButterKnife.bind(this, rootView);

        // Show the dummy content as text in a TextView.
        loadData();
        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        mProgressBar.setVisibility(View.VISIBLE);
        mLayout.setVisibility(View.INVISIBLE);
        mFab.setVisibility(View.GONE);
        switch (id){
            case ID_LOADER_DETAIL:
                return new DetailLoader(getContext(), args);
            case ID_LOADER_IMAGES:
                return new ImageLoader(getContext(), args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mLayout.setVisibility(View.VISIBLE);
        if (loader != null)
            switch (loader.getId()) {
                case ID_LOADER_DETAIL:
                    if (data != null) {
                        mSerie = ((Serie) data);
                        mName.setText(mSerie.getName());
                        mDescription.setText(mSerie.getOverview());
                        mFirstDate.setText(mSerie.getFirstAirDate());
                        String date = mSerie.getNumberOfSeasons() + " " + getContext().getResources().getString(R.string.seasons);
                        mNumberOfSeasons.setText(date);
                        if(mBanner != null)
                            Picasso.with(getContext()).load(NetworkUtils.buildURLGetPoster(mSerie.getPosterPath()))
                                    .placeholder(R.drawable.place_holder)
                                    .into(mBanner);
                        mRating.setText(String.valueOf(mSerie.getVoteAverage()).substring(0,3));
                        verifyFab();
                        if(mAppBarLayout != null)
                            mAppBarLayout.setTitle(mSerie.getOriginalName());
                    }
                    break;
                case ID_LOADER_IMAGES:
                    if(data!= null) {
                        mShowImage = ((ShowImage) data);
                        for(Backdrop poster: mShowImage.getBackdrops()) {
                            DefaultSliderView view = new DefaultSliderView(getContext());
                            view.image(NetworkUtils.buildURLGetBackdrop(poster.getFilePath()));
                            mSliderLayout.addSlider(view);
                            //view.setScaleType(BaseSliderView.ScaleType.Fit);
                        }
                    }
                    break;

            }
    }

    private void verifyFab(){
        final FloatingActionButton mFabAppBar = getActivity().findViewById(R.id.fab_app_bar);
        if(mFabAppBar == null)
            mFab.setVisibility(View.VISIBLE);
        else {
            mFab.setVisibility(View.GONE);
            mFabAppBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickFab(view);
                }
            });
        }
        myRef = database.getReference("favorites")
                .child(mAuth.getCurrentUser().getUid())
                .child(String.valueOf(mSerie.getId()));

        searchEvent= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SerieDatabaseInfo info = dataSnapshot.getValue(SerieDatabaseInfo.class);
                if(info != null)
                    isFavorite = info.isFavorite();
                else
                    isFavorite = false;
                if(isFavorite == null || !isFavorite) {
                    if(mFab != null) mFab.setImageDrawable(mContext.getDrawable(R.drawable.ic_like));
                    if(mFabAppBar!= null) mFabAppBar.setImageDrawable(mContext.getDrawable(R.drawable.ic_like));
                }else{
                    if(mFab != null) mFab.setImageDrawable(mContext.getDrawable(R.drawable.ic_like_full));
                    if(mFabAppBar!= null) mFabAppBar.setImageDrawable(mContext.getDrawable(R.drawable.ic_like_full));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(searchEvent);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private void loadData() {
        Loader loader = getLoaderManager().getLoader(ID_LOADER_DETAIL);
        Bundle args = new Bundle();
        args.putInt(DetailLoader.SERIE_ID_ARGUMENT, mTVId);
        if(loader == null) {
            getLoaderManager().initLoader(ID_LOADER_DETAIL, args, this);
            getLoaderManager().initLoader(ID_LOADER_IMAGES, args, this);
        }
        else {
            getLoaderManager().restartLoader(ID_LOADER_DETAIL, args, this);
            getLoaderManager().restartLoader(ID_LOADER_IMAGES, args, this);
        }
    }

    @Override
    public void onStop() {
        mSliderLayout.stopAutoCycle();
        myRef.removeEventListener(searchEvent);
        super.onStop();
    }
}
