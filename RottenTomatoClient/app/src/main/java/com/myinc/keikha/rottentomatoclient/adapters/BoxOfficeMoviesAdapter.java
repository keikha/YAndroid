package com.myinc.keikha.rottentomatoclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myinc.keikha.rottentomatoclient.R;
import com.myinc.keikha.rottentomatoclient.models.BoxOfficeMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keikha on 1/29/15.
 */
public class BoxOfficeMoviesAdapter extends ArrayAdapter<BoxOfficeMovie> {


    public BoxOfficeMoviesAdapter(Context context, List<BoxOfficeMovie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BoxOfficeMovie movie = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_box_office_movie , parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tvScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCasts);
        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);

        tvTitle.setText(movie.getTitle());
        tvScore.setText(String.valueOf(movie.getCriticsScore()));
        tvCast.setText(movie.getCasts());
        Picasso.with(getContext()).load(movie.getPosterURL()).into(ivPoster);

        return convertView;

    }
}
