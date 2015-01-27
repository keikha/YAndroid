package com.myinc.keikha.simpleinstgclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keikha on 1/26/15.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

   public InstagramPhotoAdapter(Context context , List<InstagramPhoto> photos)
   {
       super( context , R.layout.itemphoto , photos);
   }


    // get an item in a position, convert it into a row in the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // take the date source at position

        // get the date

        InstagramPhoto photo = getItem(position);

        // check if we using recycled view
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.itemphoto , parent , false);
            TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
            TextView tvUser = (TextView) convertView.findViewById(R.id.tvUsername);

            tvCaption.setText(photo.caption);
            imgPhoto.getLayoutParams().height = photo.hight;
            tvUser.setText(photo.username);

            // reset the image from the recycled view
            imgPhoto.setImageResource(0);

            // ask from the photos ro be added ro the imageview based on the photo url

            // background :
            Picasso.with(getContext()).load(photo.imageURL).into(imgPhoto);


        }

        return convertView;

        // lookup the subview within the templates

        //populate the subviews
    }
}
