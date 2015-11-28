package com.example.android.sunshine.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HALIM on 11/28/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

        private Context mContext;
        private int layoutResourceId;
        private ArrayList<Movie> mGridData = new ArrayList<Movie>();

        public MovieAdapter(Context mContext, int layoutResourceId, ArrayList<Movie> mGridData) {
            super(mContext, layoutResourceId, mGridData);
            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.mGridData = mGridData;
        }

        public void setGridData(ArrayList<Movie> mGridData) {
            this.mGridData = mGridData;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();

                holder.imageView = (ImageView) row.findViewById(R.id.grid_item_movie_imageview);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Movie item = mGridData.get(position);

            Picasso.with(mContext).load(item.getPoster()).into(holder.imageView);
            return row;
        }

        static class ViewHolder {
            ImageView imageView;
        }
    }
