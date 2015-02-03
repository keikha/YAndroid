package com.myinc.keikha.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myinc.keikha.gridimagesearch.R;
import com.myinc.keikha.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keikha on 1/28/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult>{


    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context , R.layout.item_image_result , images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        //
        ImageResult imageInfo = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result , parent , false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        // clear out the previous image
        ivImage.setImageResource(0);

        tvTitle.setText(Html.fromHtml(imageInfo.title));

        Picasso.with(getContext()).load(imageInfo.thumbURL).into(ivImage);

        return convertView;
    }
}
