package com.example.denis.mytvshow.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denis.mytvshow.data.Serie;
import com.example.denis.mytvshow.ui.SerieDetailActivity;
import com.example.denis.mytvshow.ui.SerieDetailFragment;
import com.example.denis.mytvshow.ui.SerieListActivity;
import com.example.denis.mytvshow.utils.CircleTransform;
import com.example.denis.mytvshow.utils.NetworkUtils;
import com.ferreira.denis.mytvshow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Denis on 27/01/2018.
 */

public class SerieAdapter
        extends RecyclerView.Adapter<SerieAdapter.ViewHolder> {

    private final SerieListActivity mParentActivity;
    private String mQuery;
    private List<Serie> mValues;
    private int mIdDefaultValue;
    private final boolean mTwoPane;
    private Context mContext;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Serie item = (Serie) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putInt(SerieDetailFragment.ARG_ITEM_ID, item.getId());
                arguments.putString(SerieDetailFragment.ARG_QUERY, mQuery);
                SerieDetailFragment fragment = new SerieDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.serie_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, SerieDetailActivity.class);
                intent.putExtra(SerieDetailFragment.ARG_ITEM_ID, item.getId());
                intent.putExtra(SerieDetailFragment.ARG_QUERY, mQuery);
                context.startActivity(intent);
            }
        }
    };

    public SerieAdapter(SerieListActivity parent,
                        boolean twoPane) {
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitleView.setText(String.valueOf(mValues.get(position).getName()));
        Picasso.with(mContext).load(NetworkUtils.buildURLGetPoster(mValues.get(position).getPosterPath()))
                .placeholder(R.drawable.place_holder)
                .transform(new CircleTransform())
                .into(holder.mBannerImageView);


        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues!=null ? mValues.size(): 0;
    }

    public void setData(List<Serie> data, String query) {
        mValues = data;
        mQuery = query;
        notifyDataSetChanged();
    }

    public void addData(List<Serie> data, String query) {
        mValues.addAll(data);
        mQuery = query;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mBannerImageView;
        final TextView mTitleView;

        ViewHolder(View view) {
            super(view);
            mTitleView = view.findViewById(R.id.title_list_content);
            mBannerImageView = view.findViewById(R.id.banner_list_content);
        }
    }
}